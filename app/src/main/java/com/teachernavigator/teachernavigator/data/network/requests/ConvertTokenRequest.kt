package com.teachernavigator.teachernavigator.data.network.requests

/**
 * Created by Arthur Korchagin on 14.10.17
 */
data class ConvertTokenRequest(val grant_type: String = "convert_token",
                               val client_id: String,
                               val client_secret: String,
                               val backend: String,
                               val token: String)