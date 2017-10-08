package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import android.support.v4.util.SparseArrayCompat
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import v_aniskin.com.trucktaxi.application.utils.DateMapper
import javax.inject.Inject

/**
 * Created by lliepmah on 07.10.17
 */

@PerParentScreen
class PostTransformerFactory
@Inject
constructor(private val context: Context,
            private val commentTransformer: CommentTransformer) {

    private val transformers = SparseArrayCompat<PostTransformer>()

    fun build(postType: PostType): EntityTransformer<PostNetwork, PostModel> =
            transformers.get(postType.ordinal, createAndSave(postType))

    private fun createAndSave(postType: PostType) =
            PostTransformer(postType, context, commentTransformer).apply { transformers.put(postType.ordinal, this) }

    private class PostTransformer(private val postType: PostType,
                                  private val context: Context, // TODO Will be needed in future
                                  private val commentTransformer: CommentTransformer) : EntityTransformer<PostNetwork, PostModel> {

        override fun transform(from: PostNetwork): PostModel =
                PostModel(
                        id = from.id ?: -1,
                        title = from.title ?: "",
                        text = from.text ?: "",
                        created = if (!from.created.isNullOrBlank()) DateMapper.mapDate(from.created!!) else "",
                        tags = from.tags.orEmpty(),
                        count_likes = from.count_likes ?: 0,
                        count_dislikes = from.count_dislikes ?: 0,
                        vote = from.vote,
                        count_comments = from.count_comments ?: 0,
                        comments = from.comments?.map(commentTransformer::transform) ?: emptyList(),
                        authorId = from.author?.id ?: -1,
                        authorName = from.author?.full_name ?: "",
                        authorAvatar = from.author?.avatars?.firstOrNull()?.avatar ?: "",
                        file = from.file,
                        type = this.postType
                )
    }

}