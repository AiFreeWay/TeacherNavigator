package com.teachernavigator.teachernavigator.domain.models

/**
 * Created by root on 11.08.17.
 */
data class Monade (var mError: String, var mStatus: String) {

    companion object {
        const val SUCCESS: String = "ok"
        const val ERROR: String = "error"
    }
}