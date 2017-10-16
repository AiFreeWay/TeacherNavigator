package com.teachernavigator.teachernavigator.domain.mappers

import android.text.TextUtils
import com.teachernavigator.teachernavigator.data.network.requests.ConvertTokenRequest
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
 * Created by root on 07.09.17
 */
class AuthMapper {

    companion object Auth {

        fun mapSingUpDataToRequest(singUpData: SingUpData): SingUpRequest =
                SingUpRequest(singUpData.email,
                        singUpData.password,
                        singUpData.full_name,
                        singUpData.birthday,
                        singUpData.work_or_learn_place,
                        singUpData.district,
                        singUpData.position,
                        singUpData.experience,
                        singUpData.unionist,
                        if(!singUpData.unionist) null else singUpData.number_of_union_ticket,
                        singUpData.phone_number.replace("(\\(|\\)| )", ""))

        fun mapSingInDataToRequest(login: String, password: String, authCredentials: AuthCredentials): SingInRequest =
                SingInRequest(
                        username = login.replace("(\\(|\\)| )", ""),
                        password = password,
                        client_id = authCredentials.clientId,
                        client_secret = authCredentials.clientSecret)

        fun mapConvertTokenRequest(token: String, socialNetwork: String, authCredentials: AuthCredentials) =
                ConvertTokenRequest(
                        backend = socialNetwork,
                        token = token,
                        client_id = authCredentials.clientId,
                        client_secret = authCredentials.clientSecret)

        fun mapSingInResponse(response: SingInResponse): Monade =
                Monade(!TextUtils.isEmpty(response.error))

        fun mapToken(response: SingInResponse): Token =
                Token(response.access_token!!, response.token_type!!, response.scope!!, response.refresh_token!!)

        // toso
        fun mapRestorePasswordDataRequest(login: String): RestorePasswordRequest {
            if (login.contains("@"))
                return RestorePasswordRequest(login, null)
            return RestorePasswordRequest(null, login)
        }

        fun mapRestorePasswordDataResponse(response: BaseResponse): Monade {
            return Monade(response.is_error)
        }

//        private fun mapPhoneNumber(number: String?): String? {
//            if (number.isNullOrBlank())
//                return null
//
//            return number?.replace(" ", "")?.replace("(", "")?.replace(")", "")
//        }
    }
}