package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.os.Bundle
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.presentation.models.ChoiceModel
import com.teachernavigator.teachernavigator.presentation.models.CommentModel
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.models.ProfileModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.PostActionsView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.ProfileFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.PostCommentsFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostActionsController
import com.teachernavigator.teachernavigator.presentation.utils.DialogUtils
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import com.teachernavigator.teachernavigator.presentation.utils.openUrl
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 08.10.17
 */
@PerParentScreen
class PostActionsController
@Inject constructor(private val router: Router,
                    private val repository: IPostsRepository) : BasePresenter<PostActionsView>(), IPostActionsController {

    override fun onOpenProfile(comment: CommentModel) =
            openProfile(comment.isMine, comment.userId)

    override fun onOpenProfile(post: PostModel) =
            openProfile(post.isMine, post.authorId)

    private fun openProfile(isMine: Boolean, userId: Int) = mView?.let {
        val bundle = Bundle()
        bundle.putBoolean(ProfileFragment.IS_MY_PROFILE_KEY, isMine)
        bundle.putInt(ProfileFragment.USER_ID_KEY, userId)
        router.navigateTo(ProfileFragment.FRAGMENT_KEY, bundle)
    } ?: Unit

    override fun onOpenFile(url: String) = mView.openUrl(url)

    override fun onPollVote(post: PostModel, choiceModel: ChoiceModel) =
            addDissposable(repository.passPoll(post.id, choiceModel.id)
                    .applySchedulers()
                    .map { it.is_error }
                    .doOnSubscribe { startProgress() }
                    .subscribe({ onPollPassed(it, post, choiceModel) }, this::onError))

    private fun onPollPassed(isError: Boolean, post: PostModel, choiceModel: ChoiceModel) {
        stopProgress()

        if (!isError) {
            mView?.updatePost(post.copy(pollPassed = true, choices = post.choices.map { if (it.id == choiceModel.id) it.copy(votes = it.votes + 1) else it }))
        } else {
            mView?.showToast(R.string.error_throwed)
        }
    }

    override fun onLike(post: PostModel) =
            addDissposable(repository.vote(post.id, true, post.type)
                    .map { it.is_error }
                    .doOnSubscribe { startProgress() }
                    .subscribe({ onVoted(true, post) }, this::onError))

    override fun onDislike(post: PostModel) =
            addDissposable(repository.vote(post.id, false, post.type)
                    .map { it.is_error }
                    .doOnSubscribe { startProgress() }
                    .subscribe({ onVoted(false, post) }, this::onError))

    override fun onSave(post: PostModel) =
            addDissposable(repository.save(post.id, post.type)
                    .applySchedulers()
                    .doOnSubscribe { startProgress() }
                    .subscribe({ onSaved(post) }, this::onError))

    override fun onSubscribe(comment: CommentModel) =
            addDissposable(DialogUtils.askPermission(mView!!.getContext(), R.string.ask_subscribe_to_user, comment.userName)
                    .flatMap {
                        repository.subscribe(comment.userId)
                                .doOnSubscribe { startProgress() }
                                .toMaybe()
                    }
                    .subscribe({ onSubscribed() }, this::onError))

    override fun onSubscribe(profile: ProfileModel) =
            addDissposable(DialogUtils.askPermission(mView!!.getContext(), R.string.ask_subscribe_to_user, profile.name)
                    .flatMap {
                        repository.subscribe(profile.id)
                                .doOnSubscribe { startProgress() }
                                .toMaybe()
                    }
                    .subscribe({ onSubscribed() }, this::onError))

    override fun onSubscribe(post: PostModel) =
            addDissposable(DialogUtils.askPermission(mView!!.getContext(), R.string.ask_subscribe_to_user, post.authorName)
                    .flatMap {
                        repository.subscribe(post.authorId)
                                .doOnSubscribe { startProgress() }
                                .toMaybe()
                    }
                    .subscribe({ onSubscribed() }, this::onError))

    override fun onComplain(post: PostModel) =
            addDissposable(DialogUtils.askPermission(mView!!.getContext(), R.string.ask_complaint_to_post, post.shortTitle)
                    .flatMap {
                        repository.complaint(post.id, post.type)
                                .doOnSubscribe { startProgress() }
                                .toMaybe()
                    }

                    .subscribe({ onComplained(post) }, this::onError))

    private fun onComplained(post: PostModel) {
        stopProgress()
        mView?.showToast(R.string.complaint_was_sent)
    }

    private fun onSubscribed() {
        stopProgress()
        mView?.showToast(R.string.you_are_subscribed)
    }

    private fun onSaved(post: PostModel) {
        stopProgress()
        mView?.showToast(R.string.post_is_saved)
    }

    override fun onComments(post: PostModel) = mView?.getParentView()?.let {
        val bundle = Bundle()
        bundle.putInt(PostCommentsFragment.ARG_POST_ID, post.id)
        bundle.putInt(PostCommentsFragment.ARG_POST_TYPE, post.type.ordinal)
        router.navigateTo(PostCommentsFragment.FRAGMENT_KEY, bundle)
    } ?: Unit

    override fun onReadMore(post: PostModel) {
        mView?.getParentView()?.let {
            val bundle = Bundle()
            bundle.putInt(PostCommentsFragment.ARG_POST_ID, post.id)
            bundle.putInt(PostCommentsFragment.ARG_POST_TYPE, post.type.ordinal)
            router.navigateTo(PostCommentsFragment.FRAGMENT_KEY, bundle)
        }
    }

    private fun onVoted(isLiked: Boolean, post: PostModel) {
        stopProgress()

        post.count_likes += when {
            post.vote != true && isLiked -> 1
            post.vote == true && !isLiked -> -1
            else -> 0
        }

        post.count_dislikes += when {
            post.vote != false && !isLiked -> 1
            post.vote == false && isLiked -> -1
            else -> 0
        }

        post.vote = isLiked

        mView?.updatePost(post)
    }

    private fun onError(error: Throwable) {
        stopProgress()
        error.printStackTrace()
        doOnError(error)
    }


    private fun startProgress() = mView?.apply {
        getParentView().startProgress()
    }

    private fun stopProgress() = mView?.apply {
        getParentView().stopProgress()
    }
}

