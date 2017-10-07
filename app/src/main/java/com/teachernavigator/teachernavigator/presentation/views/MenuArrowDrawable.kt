package com.teachernavigator.teachernavigator.presentation.views

import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import com.teachernavigator.teachernavigator.R

class MenuArrowDrawable(context: Context) : DrawerArrowDrawable(context) {

    private val mMenuToArrowAnimator: ValueAnimator
    private val mArrowToMenuAnimator: ValueAnimator

    var position: Float
        get() = progress
        set(position) {
            if (position >= 1f) {
                setVerticalMirror(true)
                setVerticalMirror(false)
            }
            progress = position
        }

    init {

        val animatorUpdateListener = ValueAnimator.AnimatorUpdateListener { animation -> position = animation.animatedValue as Float }

        mMenuToArrowAnimator = ValueAnimator.ofFloat(0f, 1f)
        mMenuToArrowAnimator.duration = 250
        mMenuToArrowAnimator.addUpdateListener(animatorUpdateListener)

        mArrowToMenuAnimator = ValueAnimator.ofFloat(1f, 0f)
        mArrowToMenuAnimator.duration = 250
        mArrowToMenuAnimator.addUpdateListener(animatorUpdateListener)

        color = ContextCompat.getColor(context, R.color.white)
    }

    fun animateDrawable(menuToArrow: Boolean) {

        if ((menuToArrow && position >= 1f) || (!menuToArrow && position <= 0f)) {
            return
        }

        val animator = if (menuToArrow) mMenuToArrowAnimator else mArrowToMenuAnimator
        if (animator.isRunning) animator.end()
        animator.start()
    }
}