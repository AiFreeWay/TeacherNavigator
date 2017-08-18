package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.base.FragmentChildMainView
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.InterviewsView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.FmtInterviewsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IInterviewsPresenter

/**
 * Created by root on 18.08.17.
 */
class InterviewsFragment : FragmentChildMainView(), InterviewsView {

    private val mPresenterFmt: IInterviewsPresenter = FmtInterviewsPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_list, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenterFmt.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenterFmt.detachView()
    }
}