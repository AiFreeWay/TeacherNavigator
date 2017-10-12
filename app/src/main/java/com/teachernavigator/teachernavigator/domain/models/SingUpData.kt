package com.teachernavigator.teachernavigator.domain.models

/**
 * Created by root on 30.08.17
 */
class SingUpData(
        var full_name: String,
        var birthday: String,
        var work_or_learn_place: String,
        var position: String,
        var experience: Int,
        var unionist: Boolean,
        var number_of_union_ticket: String,
        var email: String,
        var phone_number: String,
        var password: String,
        var district: String = "Район N9"
)