package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.base.FragmentChildMainView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtAuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IAuthPresenter


/**
 * Created by root on 22.08.17.
 */

class AuthFragment : FragmentChildMainView(), AuthView {

    companion object {
        val FRAGMENT_KEY = "auth_fragment"
    }

    private val mPresenter: IAuthPresenter = FmtAuthPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_auth, container, false)
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
