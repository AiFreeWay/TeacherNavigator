package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.application.utils.Logger
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.menu.MenuController
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.models.PostsSource
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.JobsBankFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMainPresenter
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsListFragment
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 11.08.17
 */
class AcMainPresenter : BasePresenter<MainView>(), IMainPresenter {

    @Inject
    lateinit var mRouter: Router
    @Inject
    lateinit var mAuthInteractor: IAuthInteractor
    @Inject
    lateinit var mProfileInteractor: IProfileInteractor

    private lateinit var mParentScreenComponent: ParentScreenComponent
    private var mMenuController: MenuController? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: MainView) {
        super.attachView(view)
        inject()
    }

    override fun navigateBack() {
        mRouter.exit()
    }

    override fun loadMenuItemsToViewGroup(viewGroup: ViewGroup) {
        addDissposable(mAuthInteractor.isAuthAsync()
                .subscribe({ doOnGetDataForMenu(it, viewGroup) }, this::doOnError))
    }

    override fun loadProfile() {
        addDissposable(mAuthInteractor.isAuthAsync()
                .subscribe(this::loadProfileIsAuth, this::doOnError))
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        mView!!.stopProgress()
    }

    override fun openStartFragment(savedState: Bundle?) {
        if (savedState == null)
            mRouter.newRootScreen(TapeFragment.FRAGMENT_KEY)
    }

    override fun getParentScreenComponent(): ParentScreenComponent = mParentScreenComponent

    private fun doOnGetDataForMenu(isAuthorized: Boolean, viewGroup: ViewGroup) {
        mMenuController = if (isAuthorized)
            MenuController.createControllerForAuthorizationUser(mView!!, getContext())
        else
            MenuController.createControllerForNotAuthorizationUser(mView!!, getContext())

        mMenuController!!.loadMenuItemsToViewGroup(viewGroup)

        mMenuController!!.getPresenterChannel().getInputChannel()
                .subscribe({ onMenuItemClick(it) }, { Logger.logError(it) })
    }

    private fun onMenuItemClick(item: MenuData<*>) {
        when (item.mType) {
            MenuItemsFactory.MenuItemTypes.TAPE.id -> toPostsFragment(PostsSource.Common)
            MenuItemsFactory.MenuItemTypes.MY_COMMENTS.id -> toFragment(MyCommentsFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.SAVED.id -> toPostsFragment(PostsSource.Saved)
            MenuItemsFactory.MenuItemTypes.MY_PUBLICATION.id -> toPostsFragment(PostsSource.Mine)

            MenuItemsFactory.MenuItemTypes.LOGIN.id -> ActivityRouter.openActivityAndClosePrevent(mView!!.getActivity(), AuthActivity::class.java)
            MenuItemsFactory.MenuItemTypes.SETTINGS.id -> toFragment(SettingsFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.BANK_OF_VACANCY.id -> toFragment(JobsBankFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.IMPORTANT_TO_KNOW.id -> toFragment(ImportantToKnowFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.SUPPORT.id -> toFragment(SupportFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.CHAT.id -> toFragment(ChatFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.PROFILE_HEADER.id -> toProfile()
            MenuItemsFactory.MenuItemTypes.ADD_PUBLICATION.id -> toFragment(AddPublicationFragment.FRAGMENT_KEY, false)
            MenuItemsFactory.MenuItemTypes.ABOUT.id -> toFragment(AboutFragment.FRAGMENT_KEY, false)
            MenuItemsFactory.MenuItemTypes.TAGS.id -> toFragment(TagsFragment.FRAGMENT_KEY, false)
        }
        mView!!.closeSideMenu()
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(getRootComponent(mView!!.getActivity()))
                .parentScreenModule(ParentScreenModule(mView!!))
                .build()

        mParentScreenComponent.inject(this)
    }

    private fun toPostsFragment(postsSource: PostsSource) {
//        if (!TextUtils.equals(screenKey, mLastScreenKey)) {
//
//     mLastScreenKey = screenKey

        if (postsSource == PostsSource.Mine) {
            val bundle = Bundle()
            bundle.putInt(PostsListFragment.POSTS_SOURCE_KEY, postsSource.ordinal)
            bundle.putInt(PostsListFragment.POST_TYPE_KEY, PostType.post.ordinal)
            mRouter.newRootScreen(PostsListFragment.FRAGMENT_KEY, bundle)
        } else {
            val bundle = Bundle()
            bundle.putInt(TapeFragment.POSTS_SOURCE_KEY, postsSource.ordinal)
            mRouter.newRootScreen(TapeFragment.FRAGMENT_KEY, bundle)
        }
//            if (newRoot) {
//                mRouter.newRootScreen(screenKey)
//            } else {
//                mRouter.navigateTo(screenKey)
//            }
//        }
    }

    private fun toFragment(screenKey: String, newRoot: Boolean = true) {
        if (newRoot) {
            mRouter.newRootScreen(screenKey)
        } else {
            mRouter.navigateTo(screenKey)
        }
    }

    private fun toProfile() {
        val bundle = Bundle()
        bundle.putBoolean(ProfileFragment.IS_MY_PROFILE_KEY, true)
        mRouter.navigateTo(ProfileFragment.FRAGMENT_KEY, bundle)
    }

    private fun loadProfileIsAuth(isAuthorized: Boolean) {
        if (isAuthorized) {
            addDissposable(mProfileInteractor.getProfile()
                    .subscribe(this::sendProfile, this::doOnError))
        }
    }

    private var mProfile: Profile? = null

    private fun sendProfile(profile: Profile) {
        mProfile = profile
        val data = MenuData(MenuItemsFactory.MenuItemTypes.PROFILE_HEADER.id, profile)
        mMenuController!!.getPresenterChannel().getOutputChannel().onNext(data)
    }

    override fun updateProfile() {
        loadProfileIsAuth(true)
    }

}