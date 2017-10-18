package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.models.PostsSource
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsSearchView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostSearchPresenter
import io.reactivex.Single
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 06.09.17
 */
@PerParentScreen
class PostSearchPresenter
@Inject constructor(val router: Router,
                    private val postsInteractor: IPostsInteractor) : BasePresenter<PostsSearchView>(), IPostSearchPresenter {


    override fun performSearch(text: CharSequence, publicationsContent: Pair<Boolean, Boolean>) {
        postsInteractor.setSearch(mSource, text, publicationsContent, mSearchTags)
        router.exit()
    }

    private var mSource = PostsSource.Common

    override fun setSource(id: Int?) {
        mSource = id?.let { PostsSource.values().getOrNull(id) } ?: PostsSource.Common

        postsInteractor.getFilter(mSource)?.let {
            mSearchTags.addAll(it.searchTags)
            mView?.setTags(mSearchTags.toList())
            mView?.setText(it.text)
            mView?.setChecked(it.publicationsContent)
        }
    }

    private var mTags: List<Tag>? = null
    private val mSearchTags = HashSet<String>()

    override fun searchTags(tag: CharSequence) {
        addDissposable(load()
                .map {
                    it.filter { tag.isBlank() || it.name.contains(tag, ignoreCase = true) }
                            .sortedWith(Comparator { left, right -> right.count - left.count })
                            .take(2)
                }
                .doOnSubscribe { startProgress() }
                .subscribe(this::onTagsLoaded, this::doOnError))
    }

    private fun onTagsLoaded(tags: List<Tag>) {
        stopProgress()
        mView?.setSearchTags(tags)
    }

    private fun load(): Single<List<Tag>> = mTags?.let { Single.just(it) } ?: postsInteractor.getTags()

    override fun addTag(tag: CharSequence) {
        if (tag.isBlank()) return

        mSearchTags.add(tag.toString())
        mView?.setTags(mSearchTags.toList())
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() =
            mView?.getParentView()?.setToolbarTitle(R.string.search_publication)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()
    }

    override fun navigateBack() {
        router.exit()
    }

    private fun startProgress() {
        mView?.getParentView()?.startProgress()
    }

    private fun stopProgress() {
        mView?.getParentView()?.stopProgress()
    }

}