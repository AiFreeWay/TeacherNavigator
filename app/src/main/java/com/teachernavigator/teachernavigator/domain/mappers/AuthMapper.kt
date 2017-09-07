package com.teachernavigator.teachernavigator.domain.mappers

import android.text.TextUtils
import com.teachernavigator.teachernavigator.data.network.requests.RestorePasswordRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingInRequest
import com.teachernavigator.teachernavigator.data.network.requests.SingUpRequest
import com.teachernavigator.teachernavigator.data.network.responses.BaseResponse
import com.teachernavigator.teachernavigator.data.network.responses.SingInResponse
import com.teachernavigator.teachernavigator.domain.models.AuthCredentials
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import com.teachernavigator.teachernavigator.domain.models.Token

/**
 * Created by root on 07.09.17.
 */
class AuthMapper {

    companion object Auth {

        fun mapSingUpDataToRequest(singUpData: SingUpData): SingUpRequest {
            var unionTicket: Int? = null
            if (singUpData.unionist)
                unionTicket = singUpData.number_of_union_ticket.toInt()

            return SingUpRequest(singUpData.email,
                    singUpData.password,
                    singUpData.full_name,
                    singUpData.birthday,
                    singUpData.work_or_learn_place,
                    singUpData.district,
                    singUpData.position,
                    singUpData.experience.toInt(),
                    singUpData.unionist,
                    unionTicket,
                    singUpData.phone_number)
        }

        fun mapSingUpResponse(response: BaseResponse): Monade {
            return Monade(response.is_error)
        }

        fun mapSingInDataToRequest(login: String, password: String, authCredentials: AuthCredentials): SingInRequest =
                SingInRequest(login, password,
                        authCredentials.clientId,
                        authCredentials.clientSecret,
                        authCredentials.grantType)

        fun mapSingInResponse(response: SingInResponse): Monade {
            return Monade(!TextUtils.isEmpty(response.error))
        }

        fun mapToken(response: SingInResponse): Token =
                Token(response.access_token!!, response.token_type!!, response.scope!!, response.refresh_token!!)

        fun mapRestorePasswordDataRequest(login: String): RestorePasswordRequest {
            if (login.contains("@"))
                return RestorePasswordRequest(login, null)
            return RestorePasswordRequest(null, login)
        }

        fun mapRestorePasswordDataResponse(response: BaseResponse): Monade {
            return Monade(response.is_error)
        }
    }
}