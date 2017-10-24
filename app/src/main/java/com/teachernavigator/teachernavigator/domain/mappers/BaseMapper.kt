package com.teachernavigator.teachernavigator.domain.mappers

import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.domain.models.Monade

/**
 * Created by root on 08.09.17
 */
class BaseMapper {
    companion object {
        fun mapBaseResponse(resposne: BaseResponse): Monade = Monade(resposne.is_error)
    }
}