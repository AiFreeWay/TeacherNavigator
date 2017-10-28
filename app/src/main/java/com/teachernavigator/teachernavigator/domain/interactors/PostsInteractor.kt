package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.data.models.CommentNetwork
import com.teachernavigator.teachernavigator.data.models.FileInfo
import com.teachernavigator.teachernavigator.data.models.PostCommentNetwork
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.data.network.requests.CommentRequest
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.models.Info
import com.teachernavigator.teachernavigator.presentation.models.PostsSource
import com.teachernavigator.teachernavigator.presentation.utils.applySchedulers
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 22.08.17
 */
class PostsInteractor
@Inject constructor(private val mRepository: IPostsRepository) : IPostsInteractor {


    override fun setSearch(source: PostsSource, text: CharSequence, publicationsContent: Pair<Boolean, Boolean>, searchTags: HashSet<String>) {
        mRepository.putFilter(source.ordinal, Filter(text, publicationsContent, searchTags))
    }

    override fun getFilter(source: PostsSource) = mRepository.getFilter(source.ordinal)

    override fun getPosts(postType: PostType, postsSource: PostsSource) =
            when (postType to postsSource) {

                PostType.post to PostsSource.Common -> getLatestPosts()
                PostType.post to PostsSource.Best -> getBestPosts()

                PostType.poll to PostsSource.Common -> getPolls()
                PostType.news to PostsSource.Common -> getNewsPosts()

                PostType.post to PostsSource.Mine -> getMyPosts()

                PostType.post to PostsSource.Saved -> getSavedPosts()
                PostType.importantinfo to PostsSource.Saved -> getSavedImportantInfos()
                PostType.poll to PostsSource.Saved -> getSavedPolls()
                PostType.news to PostsSource.Saved -> getSavedNews()

                PostType.post to PostsSource.Advice -> getQuestionAnswerPosts()
                PostType.importantinfo to PostsSource.Advice -> getInfoPosts(Info.ADVISE)

                else -> throw Error("Unknown Type")

            }.map { list -> getFilter(postsSource)?.let { filter -> list.filter { filter(it) } } ?: list }


    override fun sendPost(title: String, text: String, tags: List<String>, fileInfo: FileInfo?): Single<PostNetwork> =
            mRepository.sendPost(title, text, tags, fileInfo)
                    .applySchedulers()

    override fun getTrends(): Single<List<Tag>> =
            mRepository.getTrends()
                    .applySchedulers()

    override fun getTags(): Single<List<Tag>> =
            mRepository.getTags()
                    .applySchedulers()

    override fun getPost(postId: Int, postType: PostType): Single<PostNetwork> =
            mRepository.getPost(postId, postType)
                    .applySchedulers()

    override fun sendComment(postId: Int, postType: PostType, text: String): Single<CommentNetwork> =
            mRepository.comment(CommentRequest.build(postId, postType, text))
                    .applySchedulers()

    override fun getInfoPosts(currentTheme: Info): Single<List<PostNetwork>> =
            mRepository.getInfoPosts(currentTheme)
                    .map { it.results }
                    .applySchedulers()

    override fun getPolls(): Single<List<PostNetwork>> =
            mRepository.getPolls()
                    .map { it.results }
                    .applySchedulers()

    override fun getLatestPosts(): Single<List<PostNetwork>> =
            mRepository.getPosts()
                    .map { it.results }
                    .applySchedulers()

    override fun getBestPosts(): Single<List<PostNetwork>> =
            mRepository.getBestPosts()
                    .map { it.results }
                    .applySchedulers()

    override fun getNewsPosts(): Single<List<PostNetwork>> =
            mRepository.getNewsPosts()
                    .map { it.results }
                    .applySchedulers()

    override fun getSavedPosts(): Single<List<PostNetwork>> =
            mRepository.getSavedPosts()
                    .map { it.results }
                    .applySchedulers()

    private fun getSavedImportantInfos(): Single<List<PostNetwork>> =
            mRepository.getSavedImportantInfos()
                    .map { it.results }
                    .applySchedulers()

    private fun getSavedPolls(): Single<List<PostNetwork>> =
            mRepository.getSavedPolls()
                    .map { it.results }
                    .applySchedulers()

    private fun getSavedNews(): Single<List<PostNetwork>> =
            mRepository.getSavedNews()
                    .map { it.results }
                    .applySchedulers()

    private fun getQuestionAnswerPosts(): Single<List<PostNetwork>> =
            mRepository.getQuestionAnswerPosts()
                    .map { it.results }
                    .applySchedulers()

    private fun getAdvicesPosts(): Single<List<PostNetwork>> =
            mRepository.getAdvicesPosts()
                    .map { it.results }
                    .applySchedulers()


    override fun getMyPosts(): Single<List<PostNetwork>> =
            mRepository.getMyPosts()
                    .map { it.results }
                    .applySchedulers()

    override fun getUserPost(userId: Int): Single<List<PostNetwork>> =
            mRepository.getUserPost(userId)
                    .map { it.results }
                    .applySchedulers()

    override fun getMyComments(): Single<List<PostCommentNetwork>> =
            mRepository.getMyComments()
                    .map { it.toList() }
                    .applySchedulers()

}