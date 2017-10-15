package com.teachernavigator.teachernavigator.presentation.transformers

import android.support.v4.util.SparseArrayCompat
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.utils.makeLess
import v_aniskin.com.trucktaxi.application.utils.DateMapper
import javax.inject.Inject

/**
 * Created by lliepmah on 07.10.17
 */

@PerParentScreen
class PostTransformerFactory
@Inject
constructor(private val commentTransformer: CommentTransformer,
            private val choiceTransformer: ChoiceTransformer,
            private val postsRepository: IPostsRepository) {

    companion object {
        private const val SHORT_TITLE_LENGTH = 20
        private const val SHORT_TEXT_LENGTH = 120
    }

    private val transformers = SparseArrayCompat<PostTransformer>()

    fun build(postType: PostType, shorter: Boolean): EntityTransformer<PostNetwork, PostModel> =
            transformers.get(postType.ordinal, createAndSave(postType, shorter))

    private fun createAndSave(postType: PostType, shorter: Boolean) =
            PostTransformer(postType, shorter, commentTransformer, choiceTransformer, postsRepository)
                    .apply { transformers.put(postType.ordinal, this) }

    private class PostTransformer(private val postType: PostType,
                                  private val shorter: Boolean,
                                  private val commentTransformer: CommentTransformer,
                                  private val choiceTransformer: ChoiceTransformer,
                                  private val postsRepository: IPostsRepository) : EntityTransformer<PostNetwork, PostModel> {

        override fun transform(from: PostNetwork): PostModel =
                PostModel(
                        id = from.id ?: -1,
                        title = from.title?.let { if (shorter) it.makeLess(SHORT_TEXT_LENGTH) else it } ?: "",
                        shortTitle = from.title.makeLess(SHORT_TITLE_LENGTH) ?: "",
                        text = from.text?.let { if (shorter) it.makeLess(SHORT_TEXT_LENGTH) else it } ?: "",
                        created = if (!from.created.isNullOrBlank()) DateMapper.mapDate(from.created!!) else "",
                        tags = from.tags.orEmpty(),
                        count_likes = from.count_likes ?: 0,
                        count_dislikes = from.count_dislikes ?: 0,
                        vote = from.vote,
                        count_comments = from.count_comments ?: 0,
                        comments = from.comments?.map(commentTransformer::transform) ?: emptyList(),
                        authorId = from.author?.id ?: -1,
                        authorName = from.author?.full_name ?: "",
                        authorAvatar = from.author?.avatars?.firstOrNull()?.avatar,
                        type = this.postType,
                        file = from.file,
                        choices = from.choices?.map(choiceTransformer::transform) ?: emptyList(),
                        pollPassed = (postType == PostType.poll) && postsRepository.isPollPassed(from.id ?: -1)
                )
    }

}