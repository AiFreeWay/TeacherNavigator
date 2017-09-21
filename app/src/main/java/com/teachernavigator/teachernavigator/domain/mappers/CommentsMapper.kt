package com.teachernavigator.teachernavigator.domain.mappers

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.network.requests.CommentRequest
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Post
import java.util.ArrayList

/**
 * Created by root on 08.09.17.
 */
class CommentsMapper {

    companion object {

        fun mapComments(comments: List<CommentNetwork>?): List<Comment> {
            val mappedComments = ArrayList<Comment>()
            comments?.forEach { mappedComments.add(mapComment(it)) }
            return mappedComments
        }

        fun mapComment(comment: CommentNetwork): Comment=  Comment(comment.id, comment.message, mapCommentUser(comment.user), comment.author)

        fun mapCommentUser(userInComment: CommentNetwork.UserInComment?): Comment.UserInComment? {
            if (userInComment != null) {
                val comment = Comment.UserInComment(userInComment.id, userInComment.full_name, mapUserAvatar(userInComment.avatars))
                return comment
            }
            return null
        }

        fun mapUserAvatar(userAvatars: CommentNetwork.UserInComment.Avatars?): Comment.UserInComment.Avatars? {
            if (userAvatars != null)
                return Comment.UserInComment.Avatars(userAvatars.id, userAvatars.avatar)
            return null
        }

        fun mapCommentDataToRequest(post: Post, text: String): CommentRequest = CommentRequest(post.id!!, text)
    }
}