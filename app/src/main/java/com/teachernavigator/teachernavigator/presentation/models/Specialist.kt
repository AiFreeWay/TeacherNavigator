package com.teachernavigator.teachernavigator.presentation.models

import android.support.annotation.StringRes
import com.teachernavigator.teachernavigator.R

/**
 * Created by lliepmah on 06.10.17
 */
enum class Specialist(@StringRes val nameResId : Int) {

    legal_inspector(R.string.label_legal_inspector),
    occupational_safety_specialist(R.string.label_labour_protection_specialist),
    economist(R.string.label_economist),
    metodist(R.string.label_methodist),
    psychologist(R.string.label_psychologist),
    experienced(R.string.label_question_to_experienced)

}