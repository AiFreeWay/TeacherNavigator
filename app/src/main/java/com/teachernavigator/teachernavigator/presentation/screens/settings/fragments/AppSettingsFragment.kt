package com.teachernavigator.teachernavigator.presentation.screens.settings.fragments

import android.os.Bundle
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Settings
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.settings.fragments.abstractions.AppSettingsView
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.FmtAppSettingsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.settings.presenters.abstractions.IAppSettingsPresenter

/**
 * Created by root on 18.09.17.
 */
class AppSettingsFragment : BaseFragment(), AppSettingsView {

    @BindView(R.id.fmt_app_settings_swh_night_theme)
    lateinit var mSwhNightTheme: SwitchCompat
    @BindView(R.id.fmt_app_settings_swh_push_notification)
    lateinit var mSwhPushNotification: SwitchCompat
    @BindView(R.id.fmt_app_settings_swh_sound)
    lateinit var mSwhSound: SwitchCompat

    private val mPresenter: IAppSettingsPresenter = FmtAppSettingsPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_app_settings, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        mSwhNightTheme.setOnCheckedChangeListener { compoundButton, b -> mPresenter.changeNightTheme(b) }
        mSwhPushNotification.setOnCheckedChangeListener { compoundButton, b -> mPresenter.changePush(b) }
        mSwhSound.setOnCheckedChangeListener { compoundButton, b -> mPresenter.changeSound(b) }
        mPresenter.getSettings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun loadSettings(settings: Settings) {
        mSwhNightTheme.isChecked = settings.isNithThemeOn
        mSwhPushNotification.isChecked = settings.isPushOn
        mSwhSound.isChecked = settings.isSoundOn
    }

    override fun lockUi() {
    }

    override fun unlockUi() {
    }
}