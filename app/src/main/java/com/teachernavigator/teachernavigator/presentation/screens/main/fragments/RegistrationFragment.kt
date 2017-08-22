package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.base.FragmentChildMainView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.RegistrationView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtRegistrationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IRegistrationPresenter

/**
 * Created by root on 22.08.17.
 */
class RegistrationFragment : FragmentChildMainView(), RegistrationView {

    companion object {
        val FRAGMENT_KEY = "registration_fragment"
    }

    private val mPresenter: IRegistrationPresenter = FmtRegistrationPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_registration, container, false)
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
}