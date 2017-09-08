package com.teachernavigator.teachernavigator.domain.models

/**
 * Created by root on 11.08.17.
 */
open class Monade (var isError: Boolean) {

    companion object {

        val SUCCESSFULLY_MONADE = Monade(false)
        val FAILARY_MONADE = Monade(true)
    }
}