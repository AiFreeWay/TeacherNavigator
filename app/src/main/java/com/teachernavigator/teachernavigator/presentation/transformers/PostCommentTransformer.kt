package com.teachernavigator.teachernavigator.presentation.transformers

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.models.PostCommentNetwork
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.PostCommentModel
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 16.10.17
 */
@PerParentScreen
class PostCommentTransformer
@Inject
constructor(private val commentTransformer: CommentTransformer,
            private val postTransformerFactory: PostTransformerFactory) : EntityTransformer<PostCommentNetwork, PostCommentModel> {


    override fun transform(from: PostCommentNetwork): PostCommentModel =
            PostCommentModel(
                    postModel = from.postNetwork?.let { postTransformerFactory.build(getType(from), false).transform(it) },
                    commentModel = commentTransformer.transform(from)
            )

    private fun getType(from: PostCommentNetwork): PostType = when {
        from.news != null -> PostType.news
        from.poll != null -> PostType.poll
        from.post != null -> PostType.post
        from.important_info != null -> PostType.importantinfo
        else -> throw Error("Unknown Post")
    }

}