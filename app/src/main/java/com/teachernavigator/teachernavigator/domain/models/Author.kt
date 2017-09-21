package com.teachernavigator.teachernavigator.domain.models

import java.io.Serializable
import java.util.*

/**
 * Created by root on 20.09.17.
 */
class Author : Serializable {

    var id: Int? = null
    var full_name: String? = null
    var avatars: List<Avatar> = Collections.emptyList()
}