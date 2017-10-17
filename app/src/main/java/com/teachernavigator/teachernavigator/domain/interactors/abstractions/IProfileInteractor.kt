package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.domain.models.About
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.models.Specialist
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

/**
 * Created by root on 18.09.17
 */
interface IProfileInteractor {

    fun getProfile(): Single<Profile>
    fun getProfile(userId: Int): Single<Profile>
    fun exit()
    fun uploadPhoto(file: File): Single<File>

    fun loadAbout(): Single<List<About>>
    fun askSpecialist(specialist: Specialist, question: String): Single<Unit>
}