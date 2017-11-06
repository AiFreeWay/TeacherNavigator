package com.teachernavigator.teachernavigator.domain.models

/**
 * Created by Arthur Korchagin on 03.11.17
 */
class EditProfileData(
        var full_name: String?,
        var birthday: String?,
        var work_or_learn_place: String?,
        var position: String?,
        var experience: Int?,
        var unionist: String? = null,
        var number_of_union_ticket: String?,
        var email: String?,
        var phone_number: String?,
        var password: String?,
        var district: String?
) {
    var unionistBool
        set(value) {
            unionist = if (value) "True" else "False"
        }
        get() = unionist == "True"
}