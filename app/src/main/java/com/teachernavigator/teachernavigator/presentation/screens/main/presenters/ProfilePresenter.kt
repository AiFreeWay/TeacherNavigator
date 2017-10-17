package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.ImageView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.models.ProfileModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.MainActivity
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.ProfileView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IProfilePresenter
import com.teachernavigator.teachernavigator.presentation.transformers.PostTransformerFactory
import com.teachernavigator.teachernavigator.presentation.transformers.ProfileTransformer
import com.teachernavigator.teachernavigator.presentation.transformers.transformEntity
import com.teachernavigator.teachernavigator.presentation.transformers.transformListEntity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.terrakok.cicerone.Router
import java.io.File
import javax.inject.Inject

/**
 * Created by root on 18.09.17
 */
@PerParentScreen
class ProfilePresenter
@Inject

constructor(private val router: Router,
            private val profileInteractor: IProfileInteractor,
            private val postsInteractor: IPostsInteractor,
            private val profileTransformer: ProfileTransformer,
            private val postTransformerFactory: PostTransformerFactory) : BasePresenter<ProfileView>(), IProfilePresenter {

    private var mIsMyProfile: Boolean = true
    private var mUserId: Int = -1

    override fun initialLoad(isMyProfile: Boolean, userId: Int) {
        mIsMyProfile = isMyProfile
        mUserId = userId
        refresh()
    }

    override fun refresh() {
        addDissposable(Single.zip(
                getProfileSingle().transformEntity(profileTransformer),
                getPostsSingle().transformListEntity(postTransformerFactory.build(PostType.post, true)),
                BiFunction { profile: ProfileModel, posts: List<PostModel> -> profile to posts })
                .doOnSubscribe { startProgress() }
                .subscribe(this::doOnGetProfile, this::doOnError))
    }

    override fun subscribe() {
        // TODO
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()
//        val mappedList = ArrayList<ProfilePostConteainer>()
//        mappedList.add(ProfilePostConteainer(ProfileAdapterStrategy.TYPE_HEADER, Profile()))
//        mView!!.setProfile(mappedList)

//        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
        mView?.showToast(R.string.error_throwed)
    }

    private fun getPostsSingle() =
            if (mIsMyProfile)
                postsInteractor.getMyPosts()
            else
                postsInteractor.getUserPost(mUserId)

    private fun getProfileSingle() =
            if (mIsMyProfile)

                profileInteractor.getProfile()
            else
                profileInteractor.getProfile(mUserId)

    private fun startProgress() {
        mView?.startProgress()
    }

    private fun stopProgress() {
        mView?.stopProgress()
    }

    override fun navigateBack() {
        router.exit()
    }

    override fun uploadPhoto(imageView: ImageView, file: File) {
        addDissposable(profileInteractor.uploadPhoto(file)
                .doOnSubscribe { mView!!.startProgress() }
                .subscribe({ doOnLoadAvatar(imageView, file) }, this::doOnError))
    }

    override fun exit() {
        profileInteractor.exit()
        ActivityRouter.openActivityAndClosePrevent(mView!!.getActivity(), MainActivity::class.java)
    }

    private fun doOnGetProfile(data: Pair<ProfileModel, List<PostModel>>) {
        stopProgress()
        mView?.setProfile(data)
    }

    private fun doOnLoadAvatar(imageView: ImageView, file: File) {
        stopProgress()
//        ImageLoader.load(mView?.getActivity(), file, imageView)
    }

}