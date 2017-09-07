package com.teachernavigator.teachernavigator.domain.models

import android.text.TextUtils

/**
 * Created by root on 07.09.17.
 */
class Token(val accessToken: String, val tokenType: String, val scope: String, val refreshToken: String) {

    fun isExists(): Boolean =
        !TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(refreshToken)

    companion object {
        val EMPTY_TOKEN: Token = Token("", "", "", "")
    }
}
