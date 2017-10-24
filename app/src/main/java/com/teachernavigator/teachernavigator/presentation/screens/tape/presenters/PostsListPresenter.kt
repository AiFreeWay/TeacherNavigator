package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.models.PostsSource
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsListView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostsListPresenter
import com.teachernavigator.teachernavigator.presentation.transformers.PostTransformerFactory
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 18.08.17
 */

@PerParentScreen
class PostsListPresenter
@Inject
constructor(val router: Router,
            private val postsInteractor: IPostsInteractor,
            private val postTransformerFactory: PostTransformerFactory) : BasePresenter<PostsListView>(), IPostsListPresenter {

    private lateinit var mPostType: PostType
    private lateinit var mPostsSource: PostsSource

//    private var mPosts: List<PostModel>? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun initialLoad(postsSource: Int, postsType: Int) {
        mPostType = PostType.values()[postsType]
        mPostsSource = PostsSource.values()[postsSource]

        if (mPostsSource == PostsSource.Mine) {
            mView?.getParentView()?.setToolbarTitle(R.string.my_publication)
        } else if (mPostsSource == PostsSource.Advice) {
            if (PostType.post == mPostType) {
                mView?.getParentView()?.setToolbarTitle(R.string.answer_question)
            } else {
                mView?.getParentView()?.setToolbarTitle(R.string.advises)
            }
        }

//        if (mPosts.isNullOrEmpty()) {
        refresh()
//        }
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)

        mView?.apply {
            getParentView().stopProgress()
            hideRefresh()

            showNoDataText()
            Toast.makeText(getContext(), getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
        }
    }

    override fun refresh() {
        addDissposable(postsInteractor.getPosts(mPostType, mPostsSource)
                .transformListEntity(postTransformerFactory.build(mPostType, true))
                .doOnSubscribe { doOnSubscribeOnGetPosts() }
                .subscribe(this::doOnGetPosts, this::doOnError))
    }

    private fun doOnGetPosts(posts: List<PostModel>) = mView?.run {
        getParentView().stopProgress()
        hideRefresh()
//        mPosts = posts

        setPosts(posts)

        if (posts.isNotEmpty())
            hideNoDataText()
        else
            showNoDataText()
    } ?: Unit

    private fun doOnSubscribeOnGetPosts() = mView?.apply {
        getParentView().startProgress()
        hideNoDataText()
    }

}