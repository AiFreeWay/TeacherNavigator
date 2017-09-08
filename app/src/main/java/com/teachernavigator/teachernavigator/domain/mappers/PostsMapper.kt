package com.teachernavigator.teachernavigator.domain.mappers

import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.SavePostRequest
import com.teachernavigator.teachernavigator.data.network.requests.VoteRequest
import com.teachernavigator.teachernavigator.domain.models.Post
import v_aniskin.com.trucktaxi.application.utils.DateMapper
import java.util.*

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
            if (post.created != null) mappedPost.created = DateMapper.mapDate(post.created!!)
            mappedPost.tags = post.tags
            mappedPost.countLikes = post.count_likes
            mappedPost.countDislikes = post.count_dislikes
            mappedPost.vote = post.vote
            mappedPost.countComments = post.count_comments
            mappedPost.comments = CommentsMapper.mapComments(post.comments)
            return mappedPost
        }

        fun mapPostToSavePostRequest(post: Post): SavePostRequest = SavePostRequest(post.id!!, "mock")

        fun mapPostToVoteRequest(post: Post): VoteRequest = VoteRequest(post.id!!, true, "mock")
    }
}