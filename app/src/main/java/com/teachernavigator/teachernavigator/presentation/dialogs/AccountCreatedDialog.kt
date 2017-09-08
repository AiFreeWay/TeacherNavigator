package com.teachernavigator.teachernavigator.presentation.dialogs

import android.app.Dialog
import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.teachernavigator.teachernavigator.R


/**
 * Created by root on 07.09.17.
 */
class AccountCreatedDialog: DialogFragment() {

    lateinit var mOnPositiveClick: () -> Unit

    companion object {

        val FRAGMENT_MANAGER_TAG = "AccountCreatedDialog"

        fun newInstance(onPositiveClick: () -> Unit): AccountCreatedDialog {
            val accountCreatedDialog = AccountCreatedDialog()
            accountCreatedDialog.mOnPositiveClick = onPositiveClick
            return accountCreatedDialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(activity)
                .setTitle(getString(R.string.your_account_succesfuly_created))
                .setMessage(getString(R.string.account_created_text))
                .setPositiveButton(getString(R.string.do_auth), { dialog, whichButton ->  mOnPositiveClick.invoke() })
                .setCancelable(false)
                .create()
}