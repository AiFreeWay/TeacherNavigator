package com.teachernavigator.teachernavigator.domain.models

import com.google.gson.annotations.SerializedName

/**
 * Created by lliepmah on 28.09.17
 */
data class Response(
        @SerializedName("job_seeker") val employee: Author? = null
)