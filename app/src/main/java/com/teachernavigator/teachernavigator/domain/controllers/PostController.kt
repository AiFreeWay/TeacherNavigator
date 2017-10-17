//package com.teachernavigator.teachernavigator.domain.controllers
//
//import android.app.Activity
//import android.os.Bundle
//import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
//import com.teachernavigator.teachernavigator.data.repository.abstractions.IPostsRepository
//import com.teachernavigator.teachernavigator.domain.models.Comment
//import com.teachernavigator.teachernavigator.domain.models.Monade
//import com.teachernavigator.teachernavigator.domain.models.Post
//import com.teachernavigator.teachernavigator.domain.models.PostType
//import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.ProfileFragment
//import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.PostCommentsFragment
//import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
//import io.reactivex.Observable
//import io.reactivex.Single
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import ru.terrakok.cicerone.Router
//import javax.inject.Inject
//
///**
// * Created by root on 07.09.17
// */
//@PerParentScreen
//class PostController
//@Inject constructor(private val mRepository: IPostsRepository,
//                    private val router: Router) : IPostController {
//
//    override fun like(vote: Boolean, post: Post, doOnUserNotAuth: () -> Unit): Single<Monade> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun vote(postId: Int, isLike: Boolean, type: PostType): Single<Boolean> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//
//    override fun save(postId: Int, type: PostType): Single<Unit> =
//            mRepository.save(postId, type)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.newThread())
////                .map { it.is_error }
//
//
//    override fun save(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
//        return Observable.just(Monade.FAILARY_MONADE)
//    }
//
//    override fun subscribe(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
//        return Observable.just(Monade(false))
//    }
//
//    override fun subscribe(comment: Comment, doOnUserNotAuth: () -> Unit): Observable<Monade> {
//        return Observable.just(Monade.SUCCESSFULLY_MONADE)
//    }
//
//
//    override fun complain(post: Post, doOnUserNotAuth: () -> Unit): Observable<Monade> {
//        return Observable.just(Monade.SUCCESSFULLY_MONADE)
//    }
//
//    override fun openCommentsScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade> {
//        val bundle = Bundle()
//        bundle.putInt(PostCommentsFragment.ARG_POST_ID, post.id ?: -1)
//        bundle.putInt(PostCommentsFragment.ARG_POST_TYPE, PostType.post.ordinal)
//        router.navigateTo(PostCommentsFragment.FRAGMENT_KEY, bundle)
//
//        return Observable.just(TODO())
//    }
//
//    override fun openProfileScreen(post: Post, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade> {
//        val author = post.author
//        val bundle = Bundle()
//        bundle.putInt(ProfileFragment.USER_ID_KEY, author?.id!!)
//
//        ActivityRouter.openActivity(activity, bundle, ProfileFragment::class.java)
//
//        return Observable.just(Monade.FAILARY_MONADE)
//    }
//
//    override fun openProfileScreen(comment: Comment, activity: Activity, doOnUserNotAuth: () -> Unit): Observable<Monade> {
//        //TODO: mock
//        TODO("Profile should be  implemented")
//
//    }
//
//    override fun openBranch(comment: Comment, activity: Activity) {
//        TODO("Implement This Shit")
//    }
//
//    override fun openPostDetailScreen(post: Post, activity: Activity) {
//        val bundle = Bundle()
//        bundle.putInt(PostCommentsFragment.ARG_POST_ID, post.id ?: -1)
//        bundle.putInt(PostCommentsFragment.ARG_POST_TYPE, PostType.post.ordinal)
//        router.navigateTo(PostCommentsFragment.FRAGMENT_KEY, bundle)
//    }
//}