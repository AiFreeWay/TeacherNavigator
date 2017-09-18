package com.teachernavigator.teachernavigator.data.repository.abstractions

/**
 * Created by root on 22.08.17.
 */
interface IMainRepository : IAuthRepository, ITapeRepository, ISettingsRepository, IProfileRepository {

    fun getAccessToken(): String
}