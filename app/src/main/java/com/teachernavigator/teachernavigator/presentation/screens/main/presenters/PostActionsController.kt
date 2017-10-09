package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.os.Bundle
import android.util.Log.d
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.controllers.IPostController
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.PostActionsView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.PostCommentsFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostActionsController
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 08.10.17
 */
@PerParentScreen
class PostActionsController
@Inject constructor(val router: Router,
                    private val postController: IPostController) : BasePresenter<PostActionsView>(), IPostActionsController {

    override fun onLike(post: PostModel) =
            addDissposable(postController.vote(post.id, true, post.type)
                    .doOnSubscribe { startProgress() }
                    .subscribe({ onVoted(true, post) }, this::onError))

    override fun onDislike(post: PostModel) =
            addDissposable(postController.vote(post.id, false, post.type)
                    .doOnSubscribe { startProgress() }
                    .subscribe({ onVoted(false, post) }, this::onError))


    override fun onSave(post: PostModel) =
            addDissposable(postController.save(post.id, post.type)
                    .doOnSubscribe { startProgress() }
                    .subscribe({ onSaved(post) }, this::onError))

    override fun onSubscribe(post: PostModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReadMode(post: PostModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onSaved(post: PostModel) {
        d(javaClass.name, "-> resl ->$post")
    }

    override fun onComments(post: PostModel) = mView?.getParentView()?.let {

        val postType = post.type

        if (postType != null) {
            val bundle = Bundle()
            bundle.putInt(PostCommentsFragment.ARG_POST_ID, post.id)
            bundle.putInt(PostCommentsFragment.ARG_POST_TYPE, postType.ordinal)
            router.navigateTo(PostCommentsFragment.FRAGMENT_KEY, bundle)
        } else {
            onError(Error("Unknown post type"))
        }

    } ?: Unit

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
        showRefresh()
    }

    private fun stopProgress() = mView?.apply {
        getParentView().stopProgress()
        hideRefresh()
    }


}

