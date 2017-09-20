package com.teachernavigator.teachernavigator.presentation.screens.main.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IProfileFacade
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader

/**
 * Created by root on 20.09.17.
 */
class ProfileHeaderView(facade: IProfileFacade, isMyProfile: Boolean, context: Context) : RelativeLayout(context) {

    @BindView(R.id.ac_profile_iv_avatar) lateinit var mIvAvatar: ImageView
    @BindView(R.id.ac_profile_upload_photo) lateinit var mIvUploadPhoto: ImageView
    @BindView(R.id.ac_profile_iv_exit) lateinit var mIvExit: ImageView
    @BindView(R.id.ac_profile_tv_minus) lateinit var mTvMinus: TextView
    @BindView(R.id.ac_profile_tv_plus) lateinit var mTvPlus: TextView
    @BindView(R.id.ac_profile_tv_name) lateinit var mTvName: TextView
    @BindView(R.id.ac_profile_tv_rating) lateinit var mTvRating: TextView
    @BindView(R.id.ac_profile_tv_subscribers) lateinit var mTvSubscribers: TextView
    @BindView(R.id.ac_profile_tv_publications) lateinit var mTvPublications: TextView
    @BindView(R.id.ac_profile_tv_commentaries) lateinit var mTvCommentaries: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.v_profile_header, this)
        ButterKnife.bind(this, view)
        if (isMyProfile) {
            mIvExit.visibility = View.VISIBLE
            mIvUploadPhoto.visibility = View.VISIBLE

            mIvUploadPhoto.setOnClickListener { facade.onChangePhoto(mIvUploadPhoto) }
        } else {
            mIvUploadPhoto.visibility = View.GONE

            mIvUploadPhoto.setOnClickListener { }
        }

        mIvExit.visibility = View.GONE
    }

    fun loadData(profile: Profile) {
        if (profile.avatars != null && profile.avatars.isNotEmpty())
            ImageLoader.load(context, profile.avatars.get(0).avatar, mIvAvatar)

        if (profile.full_name != null)
            mTvName.setText(profile.full_name)
        else
            mTvName.setText(context.getString(R.string.not_define))

        val subscribers = if (profile.count_subscribers != null && profile.count_subscribers >0 )
            profile.count_subscribers
        else 0

        val publications = if (profile.count_publications != null && profile.count_publications >0 )
            profile.count_publications
        else 0

        if (profile.raiting != null && profile.raiting.isNotEmpty()) {
            mTvPlus.setText("+" + profile.raiting!!.get(0).count_likes.toString())
            mTvMinus.setText("-" + profile.raiting!!.get(0).count_dislikes.toString())
        }

        mTvSubscribers.setText(subscribers.toString())
        mTvPublications.setText(publications.toString())
        //mock
        mTvRating.setText("0")
        mTvCommentaries.setText("0")
    }
}