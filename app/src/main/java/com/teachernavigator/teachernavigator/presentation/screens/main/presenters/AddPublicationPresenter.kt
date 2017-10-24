package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.widget.Toast
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.PreviewFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AddPublicationView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IAddPublicationPresenter
import io.reactivex.Single
import ru.terrakok.cicerone.Router
import java.io.File
import javax.inject.Inject

/**
 * Created by root on 20.09.17
 */
@PerParentScreen
class AddPublicationPresenter
@Inject constructor(val router: Router,
                    private val postsInteractor: IPostsInteractor) : BasePresenter<AddPublicationView>(), IAddPublicationPresenter {

    var fileInfo: FileInfo? = null

    private var mTags: List<Tag>? = null

    private val mPublicationTags = HashSet<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.add_publication)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun publish(title: CharSequence, text: CharSequence) = validateAndPerform(title, text) {
        addDissposable(postsInteractor.sendPost(title.toString(), text.toString(), mPublicationTags.toList(), fileInfo)
                .doOnSubscribe { startProgress() }
                .subscribe(this::onCreated, this::doOnError))

    } ?: Unit

    private fun validateAndPerform(title: CharSequence, text: CharSequence, command: () -> Unit)
            = mView?.getContext()?.run {
        when {
            title.isBlank() -> mView?.showToast(getString(R.string.validation_empty_field, getString(R.string.title)))
            text.isBlank() -> mView?.showToast(getString(R.string.validation_empty_field, getString(R.string.publication_text)))
            mPublicationTags.isEmpty() -> mView?.showToast(getString(R.string.add_least_one_tag))
            else -> command()
        }
    }

    override fun preview(title: CharSequence, text: CharSequence) = validateAndPerform(title, text) {

        val bundle = Bundle()
        bundle.putString(PreviewFragment.KEY_TITLE, title.toString())
        bundle.putString(PreviewFragment.KEY_TEXT, text.toString())
        bundle.putString(PreviewFragment.KEY_FILE, fileInfo?.fileName)
        bundle.putStringArrayList(PreviewFragment.KEY_TAGS, ArrayList(mPublicationTags.toList()))
        router.navigateTo(PreviewFragment.FRAGMENT_KEY, bundle)

    } ?: Unit

    override fun addTag(tag: CharSequence) {
        if (tag.isBlank()) return

        mPublicationTags.add(tag.toString())
        mView?.setTags(mPublicationTags.toList())
    }

    override fun setFile(file: File?, mimeType: String?) = if (file != null && mimeType != null) {
        fileInfo = FileInfo(file.absolutePath, mimeType, file.name)
    } else Unit

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

    private fun onCreated(post: PostNetwork) {
        stopProgress()
        mView?.showToast(R.string.post_successfully_created)
        router.exit()
    }

    private fun onTagsLoaded(tags: List<Tag>) {
        stopProgress()
        mView?.setSearchTags(tags)
    }

    private fun load(): Single<List<Tag>> = mTags?.let { Single.just(it) } ?: postsInteractor.getTags()

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()
        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    private fun startProgress() = mView?.apply {
        getParentView().startProgress()
    }

    private fun stopProgress() = mView?.apply {
        getParentView().stopProgress()
    }
}