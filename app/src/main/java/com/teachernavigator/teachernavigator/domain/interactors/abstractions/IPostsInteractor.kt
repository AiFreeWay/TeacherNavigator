package com.teachernavigator.teachernavigator.domain.interactors.abstractions

import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.models.PostCommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.models.PostsSource
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by root on 22.08.17
 */
interface IPostsInteractor {

    fun getPosts(postType: PostType, postsSource: PostsSource): Single<List<PostNetwork>>

    fun getPolls(): Single<List<PostNetwork>>
    fun getLatestPosts(): Single<List<PostNetwork>>
    fun getNewsPosts(): Single<List<PostNetwork>>
    fun getUserPost(userId: Int): Single<List<PostNetwork>>
    fun getSavedPosts(): Single<List<PostNetwork>>
    fun getMyPublications(): Single<List<PostNetwork>>

    fun getInfoPosts(currentTheme: Info): Single<List<PostNetwork>>
    fun getPost(postId: Int, postType: PostType): Single<PostNetwork>
    fun sendComment(postId: Int, postType: PostType, text: String): Single<CommentNetwork>
    fun getTags(): Single<List<Tag>>
    fun getTrends(): Single<List<Tag>>

    fun sendPost(title: String, text: String, tags: List<String>,fileInfo: FileInfo?): Single<PostNetwork>

    fun getBestPosts(): Single<List<PostNetwork>>
    fun getMyComments(): Single<List<PostCommentNetwork>>
}