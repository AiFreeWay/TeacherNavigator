package com.teachernavigator.teachernavigator.domain.mappers

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Post
import v_aniskin.com.trucktaxi.application.utils.DateMapper
import java.util.ArrayList

/**
 * Created by root on 07.09.17.
 */
class PostsMapper {

    companion object {

        fun mapPosts(posts: Array<PostNetwork>): List<Post> {
            val mappedPosts = ArrayList<Post>()
            posts.forEach {
                mappedPosts.add(mapPost(it))
            }
            return mappedPosts
        }
        
        fun mapPost(post: PostNetwork): Post {
            val mappedPost = Post()
            mappedPost.id = post.id
            mappedPost.title = post.title
            mappedPost.text = post.text
            mappedPost.file = post.file
            if (post.created != null) post.created = DateMapper.mapDate(post.created!!)
            mappedPost.tags = post.tags
            mappedPost.countLikes = post.count_likes
            mappedPost.countDislikes = post.count_dislikes
            mappedPost.vote = post.vote
            mappedPost.countComments = post.count_comments
            mappedPost.comments = mapComments(post.comments)
            return mappedPost
        }

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