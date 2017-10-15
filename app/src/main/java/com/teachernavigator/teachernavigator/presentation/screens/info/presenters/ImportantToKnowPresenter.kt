package com.teachernavigator.teachernavigator.presentation.screens.info.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.PostsInteractor
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.ImportantToKnowView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.IImportantToKnowPresenter
import com.teachernavigator.teachernavigator.presentation.transformers.PostTransformerFactory
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 05.10.17
 */
@PerParentScreen
class ImportantToKnowPresenter
@Inject constructor(val router: Router,
                    private val postsInteractor: IPostsInteractor,
                    private val postTransformerFactory: PostTransformerFactory) : BasePresenter<ImportantToKnowView>(), IImportantToKnowPresenter {

    private var currentTheme: Info? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() =
            mView?.getParentView()?.setToolbarTitle(R.string.important_to_know)

    override fun attachView(view: ImportantToKnowView) {
        super.attachView(view)
        view.setThemes(Info.values().asList())
    }

    private fun loadPosts() = currentTheme?.let {
        addDissposable(postsInteractor.getInfoPosts(it)
                .transformListEntity(postTransformerFactory.build(PostType.importantinfo, false))
                .doOnSubscribe { startProgress() }
                .subscribe(this::onLoaded, this::onError))
    }

    override fun onThemeChanged(infoTheme: Info) {
        currentTheme = infoTheme
        loadPosts()
    }

    override fun refresh() =
            loadPosts() ?: Unit

    private fun onLoaded(listOfPosts: List<PostModel>) {
        stopProgress()
        mView?.setInfoPosts(listOfPosts)
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

