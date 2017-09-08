package com.teachernavigator.teachernavigator.presentation.utils

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.teachernavigator.teachernavigator.R

/**
 * Created by root on 08.09.17.
 */
class ImageLoader {

    companion object {

        fun load(context: Context, path: String?, imageView: ImageView) {
            Picasso.with(context)
                    .load(path)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(imageView)
        }
    }
}