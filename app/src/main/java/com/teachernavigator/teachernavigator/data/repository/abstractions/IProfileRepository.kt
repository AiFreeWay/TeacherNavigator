package com.teachernavigator.teachernavigator.data.repository.abstractions

import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.domain.models.About
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

/**
 * Created by root on 18.09.17
 */
interface IProfileRepository {

    fun loadAbout(): Single<List<About>>
    fun getProfile(): Single<Profile>
    fun getProfile(userId: Int): Single<Profile>
    fun exit()
    fun askSpecialist(specialist: Specialist, question: String): Single<Unit>
    fun uploadAvatar(fileInfo: FileInfo): Single<Unit>
}