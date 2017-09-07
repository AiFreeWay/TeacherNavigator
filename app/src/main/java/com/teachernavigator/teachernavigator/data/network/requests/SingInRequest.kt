package com.teachernavigator.teachernavigator.data.network.requests

/**
 * Created by root on 07.09.17.
 */
class SingInRequest(val username: String, val password: String, val client_id: String,
                    val client_secret: String, val grant_type: String)

