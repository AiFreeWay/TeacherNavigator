package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IProfileInteractor
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.menu.MenuController
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.AboutFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.ImportantToKnowFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.SupportFragment
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.TagsFragment
import com.teachernavigator.teachernavigator.presentation.screens.jobs.fragments.JobsBankFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.ProfileActivity
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.*
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMainPresenter
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import com.teachernavigator.teachernavigator.presentation.utils.notImplemented
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by root on 11.08.17.
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
    private var mLastScreenKey: String? = TapeFragment.FRAGMENT_KEY

    init {
        Logger.logDebug("created PRESENTER AcMainPresenter")
    }

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
        mLastScreenKey = ""
    }

    override fun loadMenuItemsToViewGroup(viewGroup: ViewGroup) {
        addDissposable(mAuthInteractor.isAuthAsynch()
                .subscribe({ doOnGetDataForMenu(it, viewGroup) }, this::doOnError))
    }

    override fun loadProfile() {
        addDissposable(mAuthInteractor.isAuthAsynch()
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
        if (isAuthorized)
            mMenuController = MenuController.createControllerForAuthorizationUser(mView!!, getContext())
        else
            mMenuController = MenuController.createControllerForNotAuthorizationUser(mView!!, getContext())
        mMenuController!!.loadMenuItemsToViewGroup(viewGroup)

        mMenuController!!.getPresenterChannel().getInputChannel()
                .subscribe({ onMenuItemClick(it) }, { Logger.logError(it) })
    }

    private fun onMenuItemClick(item: MenuData<*>) {
        when (item.mType) {
            MenuItemsFactory.MenuItemTypes.TAPE.id -> toFragment(TapeFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.MY_COMMENTS.id -> toFragment(MyCommentsFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.SAVED.id -> toFragment(SavedPostsFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.MY_PUBLICATION.id -> toFragment(MyPublicationsFragment.FRAGMENT_KEY)

            MenuItemsFactory.MenuItemTypes.LOGIN.id -> ActivityRouter.openActivityAndClosePrevent(mView!!.getActivity(), AuthActivity::class.java)
            MenuItemsFactory.MenuItemTypes.SETTINGS.id -> toFragment(SettingsFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.BANK_OF_VACANCY.id -> toFragment(JobsBankFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.IMPORTANT_TO_KNOW.id -> toFragment(ImportantToKnowFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.SUPPORT.id -> toFragment(SupportFragment.FRAGMENT_KEY)
            MenuItemsFactory.MenuItemTypes.CHAT.id -> mView?.notImplemented()
            MenuItemsFactory.MenuItemTypes.PROFILE_HEADER.id -> {
                val bundle = Bundle()
                bundle.putBoolean(ProfileActivity.IS_MY_PROFILE_KEY, true)
                ActivityRouter.openActivity(mView!!.getActivity(), bundle, ProfileActivity::class.java)
            }
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

    private fun toFragment(screenKey: String, newRoot: Boolean = true) {
        if (!TextUtils.equals(screenKey, mLastScreenKey)) {
            mLastScreenKey = screenKey

            if (newRoot) {
                mRouter.newRootScreen(screenKey)
            } else {
                mRouter.navigateTo(screenKey)
            }
        }
    }

    private fun loadProfileIsAuth(isAuthorized: Boolean) {
        if (isAuthorized) {
            addDissposable(mProfileInteractor.getProfile()
                    .subscribe(this::sendProfile, this::doOnError))
        }
    }

    private fun sendProfile(profile: Profile) {
        Logger.testLog("Get profile " + profile.full_name)
        val data = MenuData(MenuItemsFactory.MenuItemTypes.PROFILE_HEADER.id, profile)
        mMenuController!!.getPresenterChannel().getOutputChannel().onNext(data)
    }
}