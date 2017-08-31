package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.greenfrvr.hashtagview.HashtagView
import com.squareup.picasso.Picasso
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.utils.CircleTransform
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

/**
 * Created by root on 18.08.17.
 */
class PostHolder: BaseHolder<Post> {

    @BindView(R.id.v_post_holder_iv_avatar)
    lateinit var mIvAvatar: CircleImageView
    @BindView(R.id.v_post_holder_tv_text)
    lateinit var mTvText: TextView
    @BindView(R.id.fmt_post_holder_hv_hasttags)
    lateinit var mHvHashTags: HashtagView

    constructor(context: Context, onClick: (Post) -> Unit) : super(context, onClick) {
    }

    constructor(view: View, onClick: (Post) -> Unit) : super(view, onClick) {
        ButterKnife.bind(this, itemView)
    }

    override fun create(viewGroup: ViewGroup): BaseHolder<Post> {
        val view = viewInflater(viewGroup, R.layout.v_post_holder)
        return PostHolder(view, mOnClick!!)
    }

    override fun bind(dataModel: Post) {
        itemView.setOnClickListener { mOnClick?.invoke(dataModel) }
        Picasso.with(itemView.context)
                .load("http://mock.com")
                .placeholder(R.drawable.russian_flag)
                .error(R.drawable.russian_flag)
                .transform(CircleTransform())
                .into(mIvAvatar)

        val mockHskTags = Arrays.asList("ФГОС", "Образование", "Наука")


        mHvHashTags.setData(mockHskTags)
    }
}