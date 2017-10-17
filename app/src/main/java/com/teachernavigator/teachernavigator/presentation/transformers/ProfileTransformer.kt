package com.teachernavigator.teachernavigator.presentation.transformers

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.repository.MainRepository_Factory
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.models.ProfileModel
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 17.10.17
 */
@PerParentScreen
class ProfileTransformer
@Inject
constructor(private val postsRepository: IPostsRepository) : EntityTransformer<Profile, ProfileModel> {


    override fun transform(from: Profile): ProfileModel {
        val likes = from.raiting?.firstOrNull()?.count_likes ?: 0
        val dislikes = from.raiting?.firstOrNull()?.count_dislikes ?: 0
        val rating = (likes + dislikes) * 0.5F

        return ProfileModel(
                id = from.id ?: -1,
                name = from.full_name ?: "",
                avatarUrl = from.avatar ?: "",
                countLikes = likes,
                countDislikes = dislikes,
                countPublications = from.count_publications ?: 0,
                countSubscribers = from.count_subscribers ?: 0,
                countComments = from.count_comments ?: 0,
                ratingString = "%.1f".format(rating),
                isMine = from.id == postsRepository.currentUserId()
        )
    }

}