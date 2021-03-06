package com.teachernavigator.teachernavigator.presentation.models

import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.domain.models.Profile

/**
 * Created by root on 20.09.17.
 */
class ProfilePostConteainer : Typed {

    constructor(containerType: Int, profile: Profile) {
        this.containerType = containerType
        this.profile = profile
    }

    constructor(containerType: Int, post: PostNetwork) {
        this.containerType = containerType
        this.post = post
    }

    var containerType: Int = -1
    var profile: Profile? = null
    var post: PostNetwork? = null

    override fun getType(): Int = containerType
}