package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ICommentsInteractor
import com.teachernavigator.teachernavigator.domain.mappers.CommentsMapper
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 08.09.17.
 */
class CommentsInteractor  @Inject constructor(private val mRepository: IPostsRepository) : ICommentsInteractor {

    init {
        Logger.logDebug("created INTERACTOR CommentsInteractor")
    }

    override fun getMyComments(): Observable<List<Comment>> =
            mRepository.getMyComments()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { CommentsMapper.mapComments(it.results) }

    override fun comment(post: Post, text: String): Single<Comment> =
            mRepository.comment(CommentsMapper.mapCommentDataToRequest(post, text))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { CommentsMapper.mapComment(it) }
}