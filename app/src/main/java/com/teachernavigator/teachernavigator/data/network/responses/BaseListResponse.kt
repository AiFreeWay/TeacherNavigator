package com.teachernavigator.teachernavigator.data.network.responses

/**
 * Created by lliepmah on 24.09.17
 */
data class BaseListResponse<T>(var count: Int = 0,
                               var results: List<T> = emptyList())