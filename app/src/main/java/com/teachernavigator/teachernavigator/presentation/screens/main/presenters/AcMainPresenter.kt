package com.teachernavigator.teachernavigator.presentation.screens.main.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.view.ViewGroup
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.presentation.factories.MenuItemsFactory
import com.teachernavigator.teachernavigator.presentation.menu.MenuController
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.screens.base.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.TapeFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMainPresenter
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
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

    private lateinit var mParentScreenComponent: ParentScreenComponent
    private var mMenuController: MenuController? = null

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created PRESENTER AcMainPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun attachView(view: MainView) {
        super.attachView(view)
        inject()
    }

    override fun loadMenuItemsToViewGroup(viewGroup: ViewGroup) {
        addDissposable(mAuthInteractor.isAuth()
                .subscribe({ doOnGetDataForMenu(it, viewGroup)}, this::doOnError))
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
            mMenuController = MenuController.createControllerForAuthorizationUser(mView!!, mView!!.getActivity())
        else
            mMenuController = MenuController.createControllerForNotAuthorizationUser(mView!!, mView!!.getActivity())
        mMenuController!!.loadMenuItemsToViewGroup(viewGroup)

        mMenuController!!.getPresenterChannel().getInputChannel()
                .subscribe({ onMenuItemClick(it) }, { Logger.logError(it) })
    }

    private fun onMenuItemClick(item: MenuData<*>) {
        when(item.mType) {
            MenuItemsFactory.MenuItemTypes.LOGIN.id -> ActivityRouter.openActivity(mView!!.getActivity(), AuthActivity::class.java)
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
}