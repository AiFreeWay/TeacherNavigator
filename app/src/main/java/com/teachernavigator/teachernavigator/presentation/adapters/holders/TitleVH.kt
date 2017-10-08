package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.utils.find
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by lliepmah on 08.10.17
 */
@HolderBuilder(R.layout.v_title)
class TitleVH(itemView: View) : DefaultViewHolder<CharSequence>(itemView) {

    private val vInfoTvText: TextView = itemView.find(R.id.vTitleTvText)

    override fun bind(text: CharSequence?) {
        vInfoTvText.text = text ?: ""
    }

}