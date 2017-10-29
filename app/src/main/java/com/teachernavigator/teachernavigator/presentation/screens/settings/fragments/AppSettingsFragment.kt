package com.teachernavigator.teachernavigator.presentation.screens.settings.fragments

import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v7.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.domain.models.Settings
import com.teachernavigator.teachernavigator.presentation.adapters.holders.InfoPostVH
import com.teachernavigator.teachernavigator.presentation.models.PostModel
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

    private val exampleModel by lazy {
        PostModel(
                id = 0,
                title = getString(R.string.title_example),
                text = getString(R.string.text_example),
                created = getString(R.string.just_now),
                tags = listOf("тег1", "тег2"),
                count_likes = 5,
                count_dislikes = 5,
                vote = false,
                isMine = false,
                count_comments = 5,
                comments = emptyList(),
                authorId = 5,
                authorName = getString(R.string.author_example),
                authorAvatar = "",
                type = PostType.post,
                file = "пример.pdf",
                shortTitle = getString(R.string.title_example),
                choices = emptyList(),
                pollPassed = false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        mPresenterApp.attachView(this)
        fmtAppSettingsSwhNightTheme.setOnCheckedChangeListener { _, b -> mPresenterApp.changeNightTheme(b) }
        fmtAppSettingsSwhPushNotification.setOnCheckedChangeListener { _, b -> mPresenterApp.changePush(b) }
        fmtAppSettingsSwhSound.setOnCheckedChangeListener { _, b -> mPresenterApp.changeSound(b) }
        mPresenterApp.getSettings()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenterApp.detachView()
    }

    override fun setSettings(settings: Settings) {
        fmtAppSettingsSwhNightTheme.isChecked = settings.isNithThemeOn
        fmtAppSettingsSwhPushNotification.isChecked = settings.isPushOn
        fmtAppSettingsSwhSound.isChecked = settings.isSoundOn

        val fade = Fade()
        fade.duration = 250
        TransitionManager.beginDelayedTransition(fmtAppSettingsLPost, fade)

        val theme = when {
            settings.isNithThemeOn -> R.style.AppTheme_Night
            !settings.isNithThemeOn -> R.style.AppTheme_Day

            else -> R.style.AppTheme_Day
        }

        if (fmtAppSettingsLPost.childCount > 0) {
            fmtAppSettingsLPost.removeAllViews()
        }

        val postView = LayoutInflater.from(ContextThemeWrapper(context, theme)).inflate(R.layout.v_info_post, fmtAppSettingsLPost, true)
        val vh = InfoPostVH(postView, {}, {}, {}, {}, {}, {}, {}, null, {}, {})
        vh.bind(exampleModel)
    }

    override fun lockUi() {
    }

    override fun unlockUi() {
    }
}