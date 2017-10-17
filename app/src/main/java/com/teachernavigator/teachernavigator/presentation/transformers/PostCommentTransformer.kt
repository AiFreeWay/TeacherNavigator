package com.teachernavigator.teachernavigator.presentation.transformers

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.data.models.PostCommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
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
                    postModel = getPost(from).let { postTransformerFactory.build(it.postType ?: PostType.post, true).transform(it) },
                    commentModel = commentTransformer.transform(from)
            )

    private fun getPost(from: PostCommentNetwork): PostNetwork = when {
        from.news != null -> from.news.apply { postType = PostType.news }
        from.poll != null -> from.poll.apply { postType = PostType.poll }
        from.post != null -> from.post.apply { postType = PostType.post }
        from.important_info != null -> from.important_info.apply { postType = PostType.importantinfo }
        else -> throw Error("Unknown Post")
    }

}