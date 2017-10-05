package com.teachernavigator.teachernavigator.presentation.screens.info.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.models.About
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.AboutView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.IAboutPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by lliepmah on 05.10.17
 */
@PerParentScreen
class AboutPresenter
@Inject constructor(val router: Router,
                    private val interactor: IProfileInteractor) : BasePresenter<AboutView>(), IAboutPresenter {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.about)
        loadAbout()
    }

    private fun loadAbout() =
            addDissposable(interactor.loadAbout()
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::onLoaded, this::doOnError))

    private fun onLoaded(about: List<About>) {
        stopProgress()
        val aboutText = about.map { it.description }.reduceRight { s, acc -> "$s\n$acc" }
        mView?.setAbout(aboutText)
    }

    private fun startProgress() =
            mView?.getParentView()?.startProgress()

    private fun stopProgress() =
            mView?.getParentView()?.stopProgress()


    override fun openVk() = mView?.let {
        try {
            it.getContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.getContext().getString(R.string.social_link_vk))))
        } catch (error: ActivityNotFoundException) {
            it.showToast(R.string.browser_not_found)
        }
    }

    override fun openFb() = mView?.let {
        try {
            it.getContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.getContext().getString(R.string.social_link_fb))))
        } catch (error: ActivityNotFoundException) {
            it.showToast(R.string.browser_not_found)
        }
    }

    override fun openInstagram() = mView?.let {
        try {
            it.getContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.getContext().getString(R.string.social_link_instagram))))
        } catch (error: ActivityNotFoundException) {
            it.showToast(R.string.browser_not_found)
        }
    }
}