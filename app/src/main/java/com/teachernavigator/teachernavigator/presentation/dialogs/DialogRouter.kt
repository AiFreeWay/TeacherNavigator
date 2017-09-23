package com.teachernavigator.teachernavigator.presentation.dialogs

import android.support.v7.app.AlertDialog
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.presentation.screens.common.ParentView
import javax.inject.Inject

/**
 * Created by lliepmah on 23.09.17
 */
@PerParentScreen
class DialogRouter
@Inject
constructor(private val parentView: ParentView) {

    fun createAlertDialog() =
            AlertDialog.Builder(parentView.getActivity())


}