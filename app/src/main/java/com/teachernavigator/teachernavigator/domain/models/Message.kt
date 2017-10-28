package com.teachernavigator.teachernavigator.domain.models

import java.util.*

/**
 * Created by Arthur Korchagin on 27.10.17
 */
class Message(
        var id: Int = -1,
        var prev_id: Int = -1,
        var created: Date? = null,
        var text: String? = null,
        var user: Author? = null
)