package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.network.responses.ProfileResponse
import io.reactivex.Observable

/**
 * Created by root on 18.09.17.
 */
interface IProfileRepository {

    fun getProfile(): Observable<ProfileResponse>
}