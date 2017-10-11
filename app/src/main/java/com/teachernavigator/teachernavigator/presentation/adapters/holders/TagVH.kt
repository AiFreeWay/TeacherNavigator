package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.utils.find
import com.teachernavigator.teachernavigator.presentation.utils.listenClickBy
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by Arthur Korchagin on 10.10.17
 */
@HolderBuilder(R.layout.v_tag)
class TagVH(itemView: View,
            onTagClickListener : OnTagClickListener?) : DefaultViewHolder<Tag>(itemView) {

    private val vTagTvName: TextView = itemView.find(R.id.vTagTvName)
    private val vTagTvCount: TextView = itemView.find(R.id.vTagTvCount)

    init {
        itemView listenClickBy onTagClickListener andReturnModel { mTag }
    }

    private var mTag: Tag? = null

    override fun bind(tag: Tag?) {
        mTag = tag
        vTagTvName.text = tag?.name ?: ""
        vTagTvCount.text = tag?.count?.toString() ?: ""
    }

}

typealias OnTagClickListener = (Tag) -> Unit