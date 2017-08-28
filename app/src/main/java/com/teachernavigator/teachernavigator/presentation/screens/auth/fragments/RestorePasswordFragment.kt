package com.teachernavigator.teachernavigator.presentation.screens.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.RestorePasswordView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.FmtRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IRestorePasswordPresenter
import com.teachernavigator.teachernavigator.presentation.screens.base.BaseFragment

/**
 * Created by root on 28.08.17.
 */
class RestorePasswordFragment : BaseFragment(), RestorePasswordView {

    companion object {
        val FRAGMENT_KEY = "restore_password_fragment"
    }

    private val mPresenter: IRestorePasswordPresenter = FmtRestorePasswordPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_restore_password, container, false)
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
        setEnabledViews(false)
    }

    override fun unlockUi() {
        setEnabledViews(true)
    }

    private fun setEnabledViews(enabled: Boolean) {

    }
}