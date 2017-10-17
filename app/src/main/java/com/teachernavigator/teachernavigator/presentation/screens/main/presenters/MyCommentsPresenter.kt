package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log.d
import android.widget.Toast
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.presentation.models.PostCommentModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.MyCommentsView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMyCommentsPresenter
import com.teachernavigator.teachernavigator.presentation.transformers.PostCommentTransformer
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import com.teachernavigator.teachernavigator.presentation.utils.isNullOrEmpty
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 08.09.17
 */

@PerParentScreen
class MyCommentsPresenter
@Inject
constructor(val router: Router,
            private val postsInteractor: IPostsInteractor,
            private val postCommentTransformer: PostCommentTransformer) : BasePresenter<MyCommentsView>(), IMyCommentsPresenter {

    private var mComments: List<PostCommentModel>? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.my_comments)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun refresh() =
            addDissposable(postsInteractor.getMyComments()
                    .transformListEntity(postCommentTransformer)
                    .doOnSubscribe { doOnSubscribeOnGetPosts() }
                    .subscribe(this::doOnGetComments, this::doOnError))

    override fun initialLoad() {
        if (mComments.isNullOrEmpty()) {
            refresh()
        }
    }

    private fun doOnGetComments(comments: List<PostCommentModel>) {
        mComments = comments

        mView?.getParentView()?.stopProgress()
        mView?.hideRefresh()
        mView?.loadComments(comments)

        if (comments.isNotEmpty()) {
            mView?.hideNoDataText()
        } else {
            mView?.showNoDataText()
        }
    }

    private fun doOnSubscribeOnGetPosts() {
        mView?.getParentView()?.startProgress()
        mView?.showRefresh()
        mView?.hideNoDataText()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView?.getParentView()?.stopProgress()
        mView?.hideRefresh()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }


}