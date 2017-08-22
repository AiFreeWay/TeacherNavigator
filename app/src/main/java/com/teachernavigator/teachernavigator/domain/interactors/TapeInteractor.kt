package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.repository.abstractions.ITapeRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.ITapeInteractor
import com.teachernavigator.teachernavigator.domain.models.Post
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by root on 22.08.17.
 */
class TapeInteractor @Inject constructor(private val mRepository: ITapeRepository) : ITapeInteractor {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created INTERACTOR TapeInteractor")
    }

    override fun getBestPosts(): Observable<List<Post>> = mRepository.getBestPosts()

    override fun getInterviewsPosts(): Observable<List<Post>> = mRepository.getInterviewsPosts()

    override fun getLastPosts(): Observable<List<Post>> = mRepository.getLastPosts()

    override fun getNewsPosts(): Observable<List<Post>> = mRepository.getNewsPosts()
}