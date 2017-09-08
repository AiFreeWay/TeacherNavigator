package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.data.repository.abstractions.ITapeRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ICommentsInteractor
import com.teachernavigator.teachernavigator.domain.mappers.CommentsMapper
import com.teachernavigator.teachernavigator.domain.models.Comment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 08.09.17.
 */
class CommentsInteractor  @Inject constructor(private val mRepository: ITapeRepository) : ICommentsInteractor {

    init {
        Logger.logDebug("created INTERACTOR CommentsInteractor")
    }

    override fun getMyComments(): Observable<List<Comment>> =
            mRepository.getMyComments()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { CommentsMapper.mapComments(it.results) }

}