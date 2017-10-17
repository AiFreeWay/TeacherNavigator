package com.teachernavigator.teachernavigator.presentation.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.support.annotation.StringRes
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.screens.common.ChildView

/**
 * Created by Arthur Korchagin on 10.10.17
 */

fun ChildView?.openUrl(@StringRes urlResId: Int) =
        if (this != null) {
            try {
                getContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(urlResId))))
            } catch (error: ActivityNotFoundException) {
                showToast(R.string.browser_not_found)
            }
        } else Unit

fun ChildView?.openUrl(url: String) =
        if (this != null) {
            try {
                getContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (error: ActivityNotFoundException) {
                showToast(R.string.browser_not_found)
            }
        } else Unit