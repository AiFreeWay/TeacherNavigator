package com.teachernavigator.teachernavigator.presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.adapters.holders.BaseHolder
import java.util.*

/**
 * Created by root on 15.08.17.
 */
open class MultyRvAdapter<M>(baseHolderTemplate: BaseHolder<M>) : RecyclerView.Adapter<BaseHolder<M>>() {

    protected var mData: List<M>
    protected var mBaseHolderTemplate: BaseHolder<M> = baseHolderTemplate

    init {
        mData = Collections.emptyList()
    }

    override fun onBindViewHolder(holder: BaseHolder<M>, position: Int) = holder.bind(getItem(position))

    override fun getItemCount(): Int = mData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<M>? = mBaseHolderTemplate.create(parent)

    override fun getItemViewType(position: Int): Int = 0

    fun loadData(data: List<M>) {
        mData = data
        notifyDataSetChanged()
    }

    fun getData(): List<M> = mData

    fun getItem(position: Int): M = mData.get(position)
}