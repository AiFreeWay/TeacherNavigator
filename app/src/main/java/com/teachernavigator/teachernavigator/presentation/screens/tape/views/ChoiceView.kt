package com.teachernavigator.teachernavigator.presentation.screens.tape.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Choice

/**
 * Created by root on 20.09.17.
 */
class ChoiceView : LinearLayout {

    @BindView(R.id.v_choice_tv_choice) lateinit var mTvChoice: TextView
    @BindView(R.id.v_choice_tv_count) lateinit var mTvCount: TextView

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, choice: Choice) : super(context) {
        loadChoice(choice)
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.v_choice, this)
        ButterKnife.bind(this, view)
    }

    fun loadChoice(choice: Choice) {
        mTvChoice.setText(choice.choice_text)
        mTvCount.setText(choice.votes.toString())
    }
}