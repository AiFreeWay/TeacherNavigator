package com.teachernavigator.teachernavigator.domain.controllers

import android.app.Activity
import android.os.Bundle
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.data.network.requests.VoteRequest
import com.teachernavigator.teachernavigator.data.repository.abstractions.IMainRepository
import com.teachernavigator.teachernavigator.domain.mappers.BaseMapper
import com.teachernavigator.teachernavigator.domain.mappers.PostsMapper
import com.teachernavigator.teachernavigator.domain.models.Comment
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.ProfileActivity
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.PostCommentsActivity
import com.teachernavigator.teachernavigator.presentation.screens.tape.activities.PostDetailActivity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 07.09.17
 */
class PostController @Inject constructor(private val mRepository: IMainRepository) : IPostController {

    init {
        Logger.logDebug("created CONTROLLER PostController")
    }

    override fun like(vote: Boolean, post: Post, doOnUserNotAuth: () -> Unit): Single<Monade> =
            if (mRepository.isAuth()) {
                mRepository.vote(PostsMapper.mapPostToVoteRequest(vote, post))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .map { BaseMapper.mapBaseResponse(it) }
            } else {
                doOnUserNotAuth()
                Single.just(Monade.FAILARY_MONADE)
            }

    override fun vote(postId: Int, isLike: Boolean, type: PostType) =
            mRepository.vote(VoteRequest(postId, isLike, type.name))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { it.is_error }

    override fun save(postId: Int, type: PostType): Single<Unit> =
        mRepository.save(postId, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
//                .map { it.is_error }


    override fun save(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return mRepository.savePost(PostsMapper.mapPostToSavePostRequest(post))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { BaseMapper.mapBaseResponse(it) }
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun subscribe(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return mRepository.subscribe(PostsMapper.mapPostToSubscribeRequesttRequest(post))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map { BaseMapper.mapBaseResponse(it) }
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun subscribe(comment: Comment, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return Observable.just(Monade.SUCCESSFULLY_MONADE)
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }


    override fun complain(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth())
            return Observable.just(Monade.SUCCESSFULLY_MONADE)
        else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun openCommentsScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        if (mRepository.isAuth()) {
            val bundle = Bundle()
            bundle.putSerializable(PostCommentsActivity.POST_ID_KEY, post.id!!)
            ActivityRouter.openActivity(activity, bundle, PostCommentsActivity::class.java)
        } else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun openProfileScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        val author = post.author
        if (mRepository.isAuth() && author != null) {
            val bundle = Bundle()
            bundle.putInt(ProfileActivity.USER_ID_KEY, author.id)
            ActivityRouter.openActivity(activity, bundle, ProfileActivity::class.java)
        } else {
            doOnUserNotAuth()
        }

        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun openProfileScreen(comment: Comment, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade> {
        //TODO: mock
        if (mRepository.isAuth()) {
            val bundle = Bundle()
            bundle.putInt(PostCommentsActivity.POST_ID_KEY, 0)
            ActivityRouter.openActivity(activity, bundle, PostCommentsActivity::class.java)
        } else
            doOnUserNotAuth.invoke()
        return Observable.just(Monade.FAILARY_MONADE)
    }

    override fun openBranch(comment: Comment, activity: Activity) {
        //TODO: mock
        val bundle = Bundle()
        bundle.putInt(PostCommentsActivity.POST_ID_KEY, 0)
        ActivityRouter.openActivity(activity, bundle, PostCommentsActivity::class.java)
    }

    override fun openPostDetailScreen(post: Post, activity: Activity) {
        val bundle = Bundle()
        bundle.putInt(PostDetailActivity.POST_ID_KEY, post.id!!)
        ActivityRouter.openActivity(activity, bundle, PostDetailActivity::class.java)
    }
}