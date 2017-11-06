package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ResponseModel
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import com.teachernavigator.teachernavigator.presentation.utils.find
import com.teachernavigator.teachernavigator.presentation.utils.listenClickBy
import com.teachernavigator.teachernavigator.presentation.views.FileIconView
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by lliepmah on 28.09.17
 */
@HolderBuilder(R.layout.v_response)
class ResponseHolder(itemView: View,
                     onDownloadListener: OnDownloadListener?,
                     onUserClickListener: OnUserClickListener?) : DefaultViewHolder<ResponseModel>(itemView) {

    private val vResponseIvAvatar: ImageView = itemView.find(R.id.vResponseIvAvatar)
    private val vResponseTvName: TextView = itemView.find(R.id.vResponseTvName)
    private val vResponseTvTimeAgo: TextView = itemView.find(R.id.vResponseTvTimeAgo)
    private val vResponseVResumeFile: FileIconView = itemView.find(R.id.vResponseVResumeFile)

    private var mResponseModel: ResponseModel? = null

    init {
        vResponseVResumeFile listenClickBy onDownloadListener andReturnModel { mResponseModel }
        vResponseIvAvatar listenClickBy onUserClickListener andReturnModel { mResponseModel }
    }

    override fun bind(response: ResponseModel?) {
        mResponseModel = response
        response?.let {
            if (!it.userAvatar.isBlank()) {
                ImageLoader.load(vResponseIvAvatar.context, it.userAvatar, vResponseIvAvatar)
            }
            vResponseTvTimeAgo.text = it.timeAgo
            vResponseTvName.text = it.userName

            vResponseVResumeFile.visibility = if (it.portfolio.isBlank()) GONE else VISIBLE
            vResponseVResumeFile.setFilename(it.portfolio)
        }
    }

}

typealias OnDownloadListener = (ResponseModel) -> Unit
typealias OnUserClickListener = (ResponseModel) -> Unit