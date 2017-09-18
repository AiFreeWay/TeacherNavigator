package com.teachernavigator.teachernavigator.presentation.screens.settings.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.ProfileSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.FmtProfileSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IProfileSettingsPresenter

/**
 * Created by root on 18.09.17.
 */
class ProfileSettingsFragment : BaseFragment(), ProfileSettingsView {

    private val mPresenter: IProfileSettingsPresenter = FmtProfileSettingsPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_profile_settings, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun lockUi() {
    }

    override fun unlockUi() {
    }
}