package com.teachernavigator.teachernavigator.presentation.screens.settings.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Settings
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.AppSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IAppSettingsPresenter
import kotlinx.android.synthetic.main.fmt_app_settings.*
import javax.inject.Inject

/**
 * Created by root on 18.09.17
 */
class AppSettingsFragment : BaseFragment(), AppSettingsView {

    @Inject
    lateinit var mPresenterApp: IAppSettingsPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fmt_app_settings, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        mPresenterApp.attachView(this)
        fmtAppSettingsSwhNightTheme.setOnCheckedChangeListener { compoundButton, b -> mPresenterApp.changeNightTheme(b) }
        fmtAppSettingsSwhPushNotification.setOnCheckedChangeListener { compoundButton, b -> mPresenterApp.changePush(b) }
        fmtAppSettingsSwhSound.setOnCheckedChangeListener { compoundButton, b -> mPresenterApp.changeSound(b) }
        mPresenterApp.getSettings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenterApp.detachView()
    }

    override fun loadSettings(settings: Settings) {
        fmtAppSettingsSwhNightTheme.isChecked = settings.isNithThemeOn
        fmtAppSettingsSwhPushNotification.isChecked = settings.isPushOn
        fmtAppSettingsSwhSound.isChecked = settings.isSoundOn
    }

    override fun lockUi() {
    }

    override fun unlockUi() {
    }
}