package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.ImageView
import android.widget.Toast
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.data.models.PostNetwork
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IPostsInteractor
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.models.ProfilePostConteainer
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.MainActivity
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.ProfileView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IProfilePresenter
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import io.reactivex.functions.BiFunction
import ru.terrakok.cicerone.Router
import java.io.File
import javax.inject.Inject

/**
 * Created by root on 18.09.17.
 */
class AcProfilePresenter : BasePresenter<ProfileView>(), IProfilePresenter {

    @Inject
    lateinit var mRouter: Router
    @Inject
    lateinit var mProfileInteractor: IProfileInteractor
    @Inject
    lateinit var mPostsInteractor: IPostsInteractor

    private lateinit var mParentScreenComponent: ParentScreenComponent

    init {
        Logger.logDebug("created PRESENTER AcProfilePresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: ProfileView) {
        super.attachView(view)
        inject()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.stopProgress()
//        val mappedList = ArrayList<ProfilePostConteainer>()
//        mappedList.add(ProfilePostConteainer(ProfileAdapterStrategy.TYPE_HEADER, Profile()))
//        mView!!.loadProfile(mappedList)

        Toast.makeText(mView!!.getContext(), mView!!.getContext().getString(R.string.error_throwed), Toast.LENGTH_SHORT).show()
    }

    override fun getProfile() {
        addDissposable(mProfileInteractor.getProfile()
                .zipWith(mPostsInteractor.getMyPosts(), BiFunction<Profile, List<PostNetwork>, List<ProfilePostConteainer>> { t1, t2 ->
                    mapProfileAndPostData(t1, t2)
                })
                .doOnSubscribe { mView!!.startProgress() }
                .subscribe(this::doOnGetProfile, this::doOnError))
    }

    override fun getProfile(userId: Int) {
        addDissposable(mProfileInteractor.getProfile(userId)
                .zipWith(mPostsInteractor.getUserPost(userId), BiFunction<Profile, List<PostNetwork>, List<ProfilePostConteainer>> { t1, t2 ->
                    mapProfileAndPostData(t1, t2)
                })
                .doOnSubscribe { mView!!.startProgress() }
                .subscribe(this::doOnGetProfile, this::doOnError))
    }

    override fun navigateBack() {
        mRouter.exit()
    }

    override fun uploadPhoto(imageView: ImageView, file: File) {
        addDissposable(mProfileInteractor.uploadPhoto(file)
                .doOnSubscribe { mView!!.startProgress() }
                .subscribe({ doOnLoadAvatar(imageView, file) }, this::doOnError))
    }

    override fun exit() {
        mProfileInteractor.exit()
        ActivityRouter.openActivityAndClosePrevent(mView!!.getActivity(), MainActivity::class.java)
    }

    override fun getParentScreenComponent(): ParentScreenComponent = mParentScreenComponent

    private fun doOnGetProfile(data: List<ProfilePostConteainer>) {
        mView!!.stopProgress()
        mView!!.loadProfile(data)
    }

    private fun doOnLoadAvatar(imageView: ImageView, file: File) {
        mView!!.stopProgress()
        ImageLoader.load(mView!!.getActivity(), file, imageView)
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView!!.getActivity()))
                .parentScreenModule(ParentScreenModule(mView!!))
                .build()

        mParentScreenComponent.inject(this)
    }

    private fun mapProfileAndPostData(profile: Profile, posts: List<PostNetwork>): List<ProfilePostConteainer> {
        val mappedList = ArrayList<ProfilePostConteainer>()
//        mappedList.add(ProfilePostConteainer(ProfileAdapterStrategy.TYPE_HEADER, profile))
//
//        posts.forEach {
//            mappedList.add(ProfilePostConteainer(ProfileAdapterStrategy.TYPE_ITEM, it))
//        }
        return mappedList
    }
}