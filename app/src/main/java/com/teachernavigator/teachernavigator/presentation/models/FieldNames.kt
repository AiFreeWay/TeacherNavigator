package com.teachernavigator.teachernavigator.presentation.models

import com.teachernavigator.teachernavigator.R

/**
 * Created by Arthur Korchagin on 12.10.17
 */


object FieldNames {

    val UNKNOWN_ERROR = "неизвестная ошибка"

    fun registrationFieldnames(key: String, default : Int) = when (key) {
        "email" -> R.string.email
        "password" -> R.string.password
        "full_name" -> R.string.full_name
        "birthday" -> R.string.birthday
        "work_or_learn_place" -> R.string.work_or_learn_place
        "district" -> R.string.district
        "position" -> R.string.position
        "experience" -> R.string.experience
        "unionist" -> R.string.trade_unionist
        "number_of_union_ticket" -> R.string.indicate_number_of_unionist_ticket
        "phone_number" -> R.string.phone_number
        else -> default
    }
}