package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.models.Resume
import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import javax.inject.Inject

/**
 * Created by lliepmah on 24.09.17
 */
@PerParentScreen
class ResumeTransformer
@Inject
constructor(private val context: Context) : EntityTransformer<Resume, ResumeModel> {

    override fun transform(from: Resume): ResumeModel =
            ResumeModel(
                    id = from.id,
                    daysRemains = context.spanned(R.string.remains_days_format, from.daysRemains),
                    careerObjective = from.careerObjective,
                    districtCouncil = context.spanned(R.string.district_council_number, from.districtCouncil),
                    education = from.education,
                    experience = from.experience,

                    created = from.created,
                    expired = from.expired,

                    file = from.file,

                    userName = from.userObject?.full_name ?: context.getString(R.string.unknown),
                    userAvatar = from.userObject?.avatars?.firstOrNull()?.avatar ?: "",
                    isMine = from.isMine

            )

}