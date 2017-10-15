package com.teachernavigator.teachernavigator.presentation.utils

import android.support.annotation.IdRes
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.teachernavigator.teachernavigator.R

/**
 * Created by lliepmah on 24.09.17
 */

inline fun <reified T : View> View.find(@IdRes id: Int): T = findViewById(id)

fun Boolean?.toVisibility() =
        if (this == true) View.VISIBLE else View.GONE

fun <T> View.listenModel(listener: ((T) -> Unit)?, modelGetter: () -> T?) {
    if (listener != null) {
        setOnClickListener { modelGetter.invoke()?.apply { listener(this) } }
    }
}

fun ImageView.setImageOrPlaceholder(url: String?) {
    val isNullOrBlank = url.isNullOrBlank()
    if (!isNullOrBlank) {
        ImageLoader.load(context, url, this)
    } else {
        setImageResource(R.drawable.ic_avatar)
    }
}

fun ImageView.setImageOrHide(url: String?) {
    val isNullOrBlank = url.isNullOrBlank()
    if (!isNullOrBlank) {
        ImageLoader.load(context, url, this)
    }
    visibility = if (isNullOrBlank) GONE else VISIBLE
}

fun TextView.setTextOrHide(text: String?) {
    visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
    this.text = text ?: ""
}

infix fun <T> View.listenClickBy(listener: ((T) -> Unit)?) =
        ListenerHelper(this, listener)

class ListenerHelper<in T>(private val view: View,
                           private val listener: ((T) -> Unit)?) {

    infix fun andReturnModel(modelGetter: () -> T?) =
            if (listener != null) {
                view.setOnClickListener {
                    modelGetter.invoke()?.apply { listener.invoke(this) }
                }

            } else Unit


    infix fun andReturnModelOrHide(modelGetter: () -> T?) =
            if (listener != null) {

                view.visibility = View.VISIBLE
                view.setOnClickListener {
                    modelGetter.invoke()?.apply { listener.invoke(this) }
                }

            } else {
                view.visibility = View.GONE
            }

}