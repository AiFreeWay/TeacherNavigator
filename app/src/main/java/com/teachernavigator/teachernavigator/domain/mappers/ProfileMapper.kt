package com.teachernavigator.teachernavigator.domain.mappers

import com.teachernavigator.teachernavigator.data.network.responses.ProfileResponse
import com.teachernavigator.teachernavigator.domain.models.Profile

/**
 * Created by root on 18.09.17.
 */
class ProfileMapper {

    companion object {

        fun mapProfile(profile: ProfileResponse): Profile {
            return Profile()
        }
    }
}