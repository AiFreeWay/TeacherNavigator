package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fmt_agreement.*
import java.io.BufferedReader
import java.io.InputStreamReader

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backgroundColor = "white"
        val textColor = "black"

        val style = "<!DOCTYPE html> <html lang=\"ru\"><style type=\"text/css\"> body {background-color:%s; color:%s;} </style>"
                .format(backgroundColor, textColor)

        val htmlText = StringBuffer(style)

        BufferedReader(InputStreamReader(context.assets.open("html/agreement.html"))).lineSequence()
                .forEach { htmlText.append(it) }

        fmtAgreementWebView.loadDataWithBaseURL("",  htmlText.toString(), "text/html", "UTF-8", "")
    }

}