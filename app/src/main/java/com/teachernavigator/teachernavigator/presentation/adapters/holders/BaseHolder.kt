package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by root on 15.08.17.
 */
abstract class BaseHolder<M> : RecyclerView.ViewHolder {

    protected val mOnClick: () -> Unit?

    constructor(context: Context, onClick: () -> Unit?) : super(View(context)) {
        mOnClick = onClick
    }

    constructor(itemView: View, onClick: () -> Unit?) : super(itemView) {
        mOnClick = onClick
    }

    protected fun viewInflater(viewGroup: ViewGroup, resLayout: Int): View {
        val layoutInflater = LayoutInflater.from(itemView.context)
        return layoutInflater.inflate(resLayout, viewGroup, false)
    }

    abstract fun create(viewGroup: ViewGroup): BaseHolder<M>

    abstract fun bind(dataModel: M)
}