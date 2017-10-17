package com.teachernavigator.teachernavigator.domain.models

import java.io.Serializable
import java.util.*

/**
 * Created by root on 20.09.17
 */
data class Author(var id: Int = -1,
                  var full_name: String = "",
                  var avatar: String = ""
) : Serializable