package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.PostsInteractor
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.PostCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.transformers.PostTransformerFactory
import com.teachernavigator.teachernavigator.presentation.transformers.transformEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 08.10.17
 */
@PerParentScreen
class PostCommentsPresenter
@Inject constructor(val router: Router,
                    private val postsInteractor: PostsInteractor,
                    private val postTransformerFactory: PostTransformerFactory) : BasePresenter<PostCommentsView>(), IPostCommentsPresenter {

    private var mPostId: Int = -1
    private lateinit var mPostType: PostType

    override fun initPost(postId: Int, postTypeOrdinal: Int) {
        mPostId = postId
        mPostType = PostType.values().getOrNull(postTypeOrdinal) ?: PostType.post
        loadPost()
    }

    override fun sendComment(text: CharSequence) = if (!text.isBlank()) {

        addDissposable(postsInteractor.sendComment(mPostId, mPostType, text.toString())
                .doOnSubscribe { startProgress() }
                .flatMap { postsInteractor.getPost(mPostId, mPostType) }
                .transformEntity(postTransformerFactory.build(mPostType, false))
                .subscribe(this::onCommentSent, this::onError))

    } else Unit

    private fun onCommentSent(post: PostModel) {
        stopProgress()
        mView?.setPost(post)
        mView?.clearField()
        mView?.scrollToLast()
    }

    private fun loadPost() = addDissposable(postsInteractor.getPost(mPostId, mPostType)
            .transformEntity(postTransformerFactory.build(mPostType, false))
            .doOnSubscribe { startProgress() }
            .subscribe(this::onLoaded, this::onError))

    override fun refresh() = loadPost()

    private fun onLoaded(post: PostModel) {
        stopProgress()
        mView?.setPost(post)
    }

    private fun onError(error: Throwable) {
        stopProgress()
        error.printStackTrace()
        doOnError(error)

        val msg = error.message
        if (msg != null) {
            mView?.showToast(msg)
        } else {
            mView?.showToast(R.string.error_comments_send)
        }
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