package com.teachernavigator.teachernavigator.presentation.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.ui.base.ChildMainFragment
import com.teachernavigator.teachernavigator.presentation.ui.main.fragments.abstractions.TapeView

/**
 * Created by root on 14.08.17.
 */
class TapeFragment: ChildMainFragment(), TapeView {

    companion object {

        val FRAGMENT_KEY = "tape_fragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_tape_fragment, container, false)
        ButterKnife.bind(this, view);
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getMainView().setToolbarTitle("awd")
    }
}