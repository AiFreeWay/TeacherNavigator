package com.teachernavigator.teachernavigator.data.network.responses

/**
 * Created by root on 07.09.17
 */
open class BaseResponse {

    var status_code: Int = 200
    var is_error: Boolean = false
    var errors: Map<String, List<String>> = emptyMap()

}

// {"is_error":true,"status_code":400,"errors":{"district":["This field is required."],"number_of_union_ticket":["A valid integer is required."]}}