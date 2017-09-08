package com.teachernavigator.teachernavigator.domain.mappers

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.domain.models.Comment
import java.util.ArrayList

/**
 * Created by root on 08.09.17.
 */
class CommentsMapper {

    companion object {

        fun mapComments(comments: List<CommentNetwork>?): List<Comment> {
            val mappedComments = ArrayList<Comment>()
            comments?.forEach { mappedComments.add(Comment(it.id, it.message, mapCommentUser(it.user))) }
            return mappedComments
        }

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
    }
}