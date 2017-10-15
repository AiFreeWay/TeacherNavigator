package com.teachernavigator.teachernavigator.presentation.utils

/**
 * Created by Arthur Korchagin on 12.10.17
 */

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.support.annotation.StringRes
import android.text.InputType
import com.afollestad.materialdialogs.MaterialDialog
import com.teachernavigator.teachernavigator.R
import io.reactivex.Maybe
import java.util.*

object DialogUtils {

    fun showDateDialog(context: Context, currentDate: Date?, minDate: Date?, maxDate: Date?,
                       onDateChangedListener: (Date) -> Unit): DatePickerDialog {

        val calendar = Calendar.getInstance()
        calendar.time = currentDate ?: Date()

        val datePickerDialog = DatePickerDialog(context, R.style.Dialog,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    calendar.set(year, monthOfYear, dayOfMonth)
                    onDateChangedListener(calendar.time)
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))


        val datePicker = datePickerDialog.datePicker
        if (minDate != null) {
            datePicker.minDate = minDate.time
        }
        if (maxDate != null) {
            datePicker.maxDate = maxDate.time
        }

        datePickerDialog.show()

        return datePickerDialog
    }


    fun showTimeDialog(context: Context, date: Date?, onTimeChangedListener: (Date) -> Unit) {

        val calendar = Calendar.getInstance()
        calendar.time = date ?: Date()

        TimePickerDialog(context, R.style.Dialog,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    onTimeChangedListener(calendar.time)
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
                .show()
    }


    fun askPermission(context: Context, @StringRes titleRes: Int, vararg formatArgs: String) =

            Maybe.create<Unit> {
                val message = context.getString(titleRes, *formatArgs)
                MaterialDialog.Builder(context)
                        .content(message)
                        .positiveText(android.R.string.ok)
                        .negativeText(android.R.string.cancel)
                        .onPositive { _, _ -> it.onSuccess(Unit) }
                        .onNegative { _, _ -> it.onComplete() }
                        .show()
            }!!


    fun showMessage(context: Context, @StringRes titleRes: Int, message: String) =
            MaterialDialog.Builder(context)
                    .title(titleRes)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .show()

    fun showProgressDialog(context: Context, @StringRes titleRes: Int) =

            MaterialDialog.Builder(context)
                    .content(titleRes)
                    .progress(true, 0)
                    .show()

    fun showConfirmDialog(context: Context, @StringRes titleRes: Int, @StringRes positiveText: Int,
                          @StringRes negativeText: Int, onChooseListener: (Boolean) -> Unit) =

            MaterialDialog.Builder(context)
                    .title(titleRes)
                    .positiveText(positiveText)
                    .onPositive { _, _ -> onChooseListener(true) }
                    .negativeText(negativeText)
                    .onNegative { _, _ -> onChooseListener(false) }
                    .show()


    fun showList(context: Context, titleRes: Int, list: List<String>, listener: (pos: Int) -> Unit) =

            MaterialDialog.Builder(context)
                    .title(titleRes)
                    .items(list)
                    .itemsCallback { _, _, position, _ -> listener(position) }
                    .show()

    fun showNumberDialog(context: Context, @StringRes titleRes: Int, @StringRes hintRes: Int, listener: (Int) -> Unit) =
            MaterialDialog.Builder(context)
                    .title(titleRes)
                    .inputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                    .input(hintRes, 0, true) { _, _ -> }
                    .onPositive { dialog, _ -> listener(dialog.inputEditText?.text?.toString()?.toIntOrNull() ?: 0) }
                    .show()


}