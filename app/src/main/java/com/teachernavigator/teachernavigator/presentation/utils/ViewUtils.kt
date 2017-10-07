package com.teachernavigator.teachernavigator.presentation.utils

import android.support.annotation.IdRes
import android.view.View

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

infix fun <T> View.listenClickBy(listener: ((T) -> Unit)?) =
        ListenerHelper(this, listener)

class ListenerHelper<in T>(private val view: View,
                           private val listener: ((T) -> Unit)?) {

    infix fun andReturnModel(modelGetter: () -> T?) =
            if (listener != null) {
                view.setOnClickListener { modelGetter.invoke()?.apply { listener.invoke(this) } }
            } else Unit

}