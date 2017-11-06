package com.teachernavigator.teachernavigator.presentation.views

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.annotation.AttrRes
import android.support.annotation.StyleRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.teachernavigator.teachernavigator.R
import kotlinx.android.synthetic.main.v_file_icon.view.*

/**
 * Created by Arthur Korchagin on 16.10.17
 */

class FileIconView : FrameLayout {

    constructor(context: Context) : super(context) {
        initLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initLayout(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int, @StyleRes defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initLayout(context)
    }

    private fun initLayout(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.v_file_icon, this)
    }

    fun setFilename(filename: String) {
        val ext = getExtension(filename)
        tvFileExtension.text = ext
        tvFileExtension.setBackgroundColor(getColorByExt(ext))
    }

    private fun getColorByExt(ext: String) = ContextCompat.getColor(context, when (ext) {
        "PDF" -> R.color.bright_red
        "DOC" -> R.color.bright_blue
        "JPEG", "JPG", "PNG" -> R.color.orange
        "" -> R.color.transparent
        else -> R.color.colorAccent
    })

    private fun getExtension(fileName: String): String {
        if (fileName.isBlank()) {
            return ""
        }

        val dotInd = fileName.lastIndexOf('.')
        val sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'))
        return if (dotInd <= sepInd) {
            ""
        } else {
            fileName.substring(dotInd + 1).toUpperCase()
        }
    }

}