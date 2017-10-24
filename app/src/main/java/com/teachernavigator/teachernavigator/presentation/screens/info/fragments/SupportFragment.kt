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
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.SupportView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.ISupportPresenter
import com.teachernavigator.teachernavigator.presentation.utils.notImplemented
import kotlinx.android.synthetic.main.fmt_support.*
import javax.inject.Inject

/**
 * Created by lliepmah on 05.10.17
 */
class SupportFragment : BaseFragment(), SupportView {

    companion object {
        val FRAGMENT_KEY = "support_fragment"
    }

    @Inject
    lateinit var supportPresenter: ISupportPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_support, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fmtSupportTvLegalInspector.setOnClickListener { supportPresenter.openLegalInspector() }
        fmtSupportTvLabourProtectionSpecialist.setOnClickListener { supportPresenter.openLabourProtection() }
        fmtSupportTvEconomist.setOnClickListener { supportPresenter.openEconomist() }
        fmtSupportTvMethodist.setOnClickListener { supportPresenter.openMethodist() }
        fmtSupportTvPsychologist.setOnClickListener { supportPresenter.openPsychologist() }

        fmtSupportBtnAnswerQuestion.setOnClickListener { supportPresenter.openAnswerQuestion() }
        fmtSupportBtnAdvises.setOnClickListener { supportPresenter.openAdvises() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        supportPresenter.attachView(this)
    }

}