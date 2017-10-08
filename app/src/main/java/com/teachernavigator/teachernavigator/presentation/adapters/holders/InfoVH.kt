package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.utils.find
import com.teachernavigator.teachernavigator.presentation.utils.listenClickBy
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by lliepmah on 08.10.17
 */
@HolderBuilder(R.layout.v_info)
class InfoVH(itemView: View,
             onInfoClick: OnInfoClick) : DefaultViewHolder<Info>(itemView) {

    private val context = itemView.context

    private val vInfoTvText: TextView = itemView.find(R.id.vInfoTvText)
    private val vInfoRoot: View = itemView.find(R.id.vInfoRoot)

    private var mInfo: Info? = null

    init {
        vInfoRoot listenClickBy onInfoClick andReturnModel { mInfo }
    }

    override fun bind(info: Info?) {
        mInfo = info
        vInfoTvText.setText(info?.title ?: R.string.empty)
        vInfoTvText.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, info?.icon ?: R.drawable.ic_legal), null, null, null)
    }
}

typealias OnInfoClick = (Info) -> Unit