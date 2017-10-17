package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ProfileModel
import com.teachernavigator.teachernavigator.presentation.utils.find
import com.teachernavigator.teachernavigator.presentation.utils.setImageOrPlaceholder
import com.teachernavigator.teachernavigator.presentation.utils.setTextOrHide
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by Arthur Korchagin on 17.10.17
 */
@HolderBuilder(R.layout.v_profile_header)
class ProfileVH(itemView: View) : DefaultViewHolder<ProfileModel>(itemView) {

    private val ivAvatar: ImageView = itemView.find(R.id.ac_profile_iv_avatar)
    private val ivExit: ImageView = itemView.find(R.id.ac_profile_iv_exit)
    private val ivUploadPhoto: ImageView = itemView.find(R.id.ac_profile_upload_photo)
    private val tvMinus: TextView = itemView.find(R.id.ac_profile_tv_minus)
    private val tvPlus: TextView = itemView.find(R.id.ac_profile_tv_plus)
    private val tvName: TextView = itemView.find(R.id.ac_profile_tv_name)
    private val tvRating: TextView = itemView.find(R.id.ac_profile_tv_rating)
    private val tvSubscribers: TextView = itemView.find(R.id.ac_profile_tv_subscribers)
    private val tvPublications: TextView = itemView.find(R.id.ac_profile_tv_publications)
    private val tvCommentaries: TextView = itemView.find(R.id.ac_profile_tv_commentaries)


    init {
//        ivExit.setOnClickListener {  }
    }

    private var mModel: ProfileModel? = null

    override fun bind(model: ProfileModel?) {
        mModel = model

        ivAvatar.setImageOrPlaceholder(model?.avatarUrl)
        tvName.setTextOrHide(model?.name)

//        ivUploadPhoto hide if not mine
        tvRating.setTextOrHide(model?.ratingString)
        tvSubscribers.setTextOrHide(model?.countSubscribers?.toString())
        tvPublications.setTextOrHide(model?.countPublications?.toString())
        tvCommentaries.setTextOrHide(model?.countComments?.toString())
        tvMinus.setTextOrHide(model?.countDislikes?.toString()?.let { "-$it" })
        tvPlus.setTextOrHide(model?.countLikes?.toString()?.let { "+$it" })
    }

}