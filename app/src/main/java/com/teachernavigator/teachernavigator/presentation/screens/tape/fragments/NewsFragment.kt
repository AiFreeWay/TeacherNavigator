package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.base.FragmentChildMainView
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.NewsView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.FmtNewsPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.INewsPresenter

/**
 * Created by root on 18.08.17.
 */
class NewsFragment : FragmentChildMainView(), NewsView {

    private val mPresenterFmt: INewsPresenter = FmtNewsPresenter()

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