package com.teachernavigator.teachernavigator.presentation.views

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.annotation.AttrRes
import android.support.annotation.StyleRes
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ChoiceModel
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import kotlinx.android.synthetic.main.v_poll_choice.view.*


/**
 * Created by Arthur Korchagin on 16.10.17
 */

class PollChoiceView : FrameLayout {

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
        LayoutInflater.from(context).inflate(R.layout.v_poll_choice, this)
    }

    fun setChoiсe(choice: ChoiceModel, postModel: PostModel) {

        val sum = postModel.choices.sumBy { it.votes }
        val votes = choice.votes

        val percent = if (sum == 0) 0F else votes.toFloat() / sum
        val guidePercent = 1F - percent * 0.4F - 0.1f

        vPollTvAnswersCount.text = votes.toString()
        vPollTvQuestion.text = choice.choiceText

        val constraintSet = ConstraintSet()
        constraintSet.clone(vPollRoot)
        constraintSet.setGuidelinePercent(R.id.vPollGuidelineChoice, guidePercent)
        constraintSet.applyTo(vPollRoot)

    }

}
