package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment

/**
 * Created by Arthur Korchagin on 18.10.17
 */
class AgreementFragment : BaseFragment() {

    companion object {
        val FRAGMENT_KEY = "AgreementFragment"
    }

    override fun onStart() {
        super.onStart()
        getParentView().setToolbarTitle(R.string.agreement)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_agreement, container, false)

}