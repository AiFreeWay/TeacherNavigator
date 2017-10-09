package com.teachernavigator.teachernavigator.presentation.screens.info.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.TagsView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.ITagsPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 10.10.17
 */
@PerParentScreen
class TagsPresenter
@Inject constructor(val router: Router,
                    private val postsInteractor: IPostsInteractor) : BasePresenter<TagsView>(), ITagsPresenter {

    private var mTags: List<Tag> = emptyList()

    override var text: CharSequence = ""
        set(value) {
            field = value
            updateTags()
        }

    override var onlyTrends: Boolean = false
        set(value) {
            field = value
            updateTags()
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() =
            mView?.getParentView()?.setToolbarTitle(R.string.tags)

    override fun refresh() =
            addDissposable(postsInteractor.getTags()
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onLoaded, this::onError))

    private fun onLoaded(tags: List<Tag>) {
        stopProgress()
        mTags = tags
        updateTags()
    }

    private fun updateTags() =
            mView?.setTags(
                    mTags.filter { text.isBlank() || it.name.contains(text) }
                            .sortedWith(Comparator { left, right -> right.count - left.count })
                            .take(if (onlyTrends) 5 else mTags.size))


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