package com.teachernavigator.teachernavigator.presentation.screens.info.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.AboutView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.IAboutPresenter
import com.teachernavigator.teachernavigator.presentation.utils.notImplemented
import kotlinx.android.synthetic.main.fmt_about.*
import javax.inject.Inject

/**
 * Created by lliepmah on 05.10.17
 */
class AboutFragment : BaseFragment(), AboutView {

    companion object {
        val FRAGMENT_KEY = "about_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var aboutPresenter: IAboutPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_about, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fmtAboutSocialVk.setOnClickListener { aboutPresenter.openVk() }
        fmtAboutSocialTw.setOnClickListener { notImplemented() }
        fmtAboutSocialFb.setOnClickListener { aboutPresenter.openFb() }
        fmtAboutInstagram.setOnClickListener { aboutPresenter.openInstagram() }
        fmtAboutYoutube.setOnClickListener { notImplemented() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        aboutPresenter.attachView(this)
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun setAbout(text: String) {
        fmtAboutTvText.text = text
    }

}