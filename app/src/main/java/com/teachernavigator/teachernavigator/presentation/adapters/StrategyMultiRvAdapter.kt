package com.teachernavigator.teachernavigator.presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.presentation.adapters.holders.BaseHolder
import com.teachernavigator.teachernavigator.presentation.models.Typed
import java.util.*

/**
 * Created by root on 15.08.17.
 */
class StrategyMultiRvAdapter<M: Typed>(adapterStrategy: AdapterStrategy<M>) : RecyclerView.Adapter<BaseHolder<M>>() {

    protected var mData: List<M>
    protected var mAdapterStrategy: AdapterStrategy<M> = adapterStrategy

    init {
        mData = Collections.emptyList()
    }

    override fun onBindViewHolder(holder: BaseHolder<M>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = mData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<M>? = mAdapterStrategy
            .createHolder(parent, viewType)

    override fun getItemViewType(position: Int): Int = getItem(position).getType()

    fun loadData(data: List<M>) {
        mData = data
        notifyDataSetChanged()
    }

    fun getData(): List<M> = mData

    fun getItem(position: Int): M = mData.get(position)
}