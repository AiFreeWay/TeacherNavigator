package com.teachernavigator.teachernavigator.presentation.screens.info.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.AskSpecialistView
import com.teachernavigator.teachernavigator.presentation.screens.info.presenters.abstractions.IAskSpecialistPresenter
import kotlinx.android.synthetic.main.fmt_ask_question.*
import javax.inject.Inject

/**
 * Created by lliepmah on 05.10.17
 */
class AskSpecialistFragment : BaseFragment(), AskSpecialistView {

    companion object {
        val FRAGMENT_KEY = "ask_specialist_fragment"

        val SPECIALIST_KEY = "specialist"
    }

    @Inject
    lateinit var askSpecialistPresenter: IAskSpecialistPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_ask_question, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askQuestionBtnAsk.setOnClickListener { sendQuestion() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        askSpecialistPresenter.attachView(this)
        val specialist = Specialist.values().getOrNull(arguments.getInt(SPECIALIST_KEY))
        askQuestionTvAskQuestion.text =
                getString(R.string.ask_question_to_f,
                        getString(if (specialist == Specialist.experienced) R.string.to_experienced else R.string.to_specialist))

        askSpecialistPresenter.specialist = specialist
    }

    override fun cleanField() {
        askQuestionEtMessage.setText("")
    }

    private fun sendQuestion() =
            askSpecialistPresenter.askQuestion(askQuestionEtMessage.text.toString())

}