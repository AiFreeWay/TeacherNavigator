package com.teachernavigator.teachernavigator.domain.mappers

import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.SavePostRequest
import com.teachernavigator.teachernavigator.data.network.requests.SubscribeRequest
import com.teachernavigator.teachernavigator.data.network.requests.VoteRequest
import com.teachernavigator.teachernavigator.data.network.responses.PostsResponse
import com.teachernavigator.teachernavigator.domain.models.Post
import v_aniskin.com.trucktaxi.application.utils.DateMapper
import java.util.*

/**
 * Created by root on 07.09.17.
 */
class PostsMapper {

    companion object {

        fun mapPosts(posts: PostsResponse): List<Post> {
            val mappedPosts = ArrayList<Post>()
            posts.results.forEach {
                mappedPosts.add(mapPost(it))
            }
            return mappedPosts
        }

        fun mapPostsFromArray(posts: Array<PostNetwork>): List<Post> {
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
            mappedPost.countLikes = replaceTextToInt(post.count_likes)
            mappedPost.countDislikes = replaceTextToInt(post.count_dislikes)
            mappedPost.vote = post.vote
            mappedPost.countComments = post.count_comments
            mappedPost.comments = CommentsMapper.mapComments(post.comments)
            mappedPost.author = post.author
            mappedPost.choices = post.choices
            return mappedPost
        }

        fun mapPostToSavePostRequest(post: Post): SavePostRequest = SavePostRequest(post.id!!, "mock")

        fun mapPostToSubscribeRequesttRequest(post: Post): SubscribeRequest = SubscribeRequest(post.author!!.id)

        fun mapPostToVoteRequest(vote: Boolean, post: Post): VoteRequest = VoteRequest(post.id!!, vote, "post")

        private fun replaceTextToInt(number: Int?): Int {
            if (number == null) {
                return 0
            }
            return number
        }
    }
}