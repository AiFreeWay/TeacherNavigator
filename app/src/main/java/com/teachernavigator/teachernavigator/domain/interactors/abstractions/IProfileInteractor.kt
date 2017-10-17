package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.domain.models.About
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import io.reactivex.Single

/**
 * Created by root on 18.09.17
 */
interface IProfileInteractor {

    fun getProfile(): Single<Profile>
    fun getProfile(userId: Int): Single<Profile>
    fun exit()

    fun loadAbout(): Single<List<About>>
    fun askSpecialist(specialist: Specialist, question: String): Single<Unit>

    fun uploadAvatar(fileInfo: FileInfo): Single<Profile>
}