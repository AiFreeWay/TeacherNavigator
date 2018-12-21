package com.teachernavigator.teachernavigator.presentation.screens.common

import android.app.Activity
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.application.TeacherNavigatopApp
import com.teachernavigator.teachernavigator.application.di.components.RootComponent
import com.teachernavigator.teachernavigator.application.utils.Logger
import com.teachernavigator.teachernavigator.data.cache.CacheController
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

/**
 * Created by root on 11.08.17
 */
abstract class BasePresenter<V : BaseView> : LifecycleObserver, ViewAttacher<V> {

    protected val mDisposables: CompositeDisposable = CompositeDisposable()
    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
        mView?.lifecycle?.addObserver(this)
    }

    override fun detachView() {
        mView?.lifecycle?.removeObserver(this)
        mView = null
    }

    protected fun addDissposable(disposable: Disposable) {
        mDisposables.add(disposable)
    }

    open protected fun doOnError(error: Throwable) {
        if (BuildConfig.DEBUG) Logger.logError(error)

        if ((error as? HttpException)?.code() == 401) {
            addDissposable(
                CacheController.logout()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ openAuthScreen() }, { openAuthScreen() })
            )
        }
    }

    private fun openAuthScreen() {
        ((mView as? ChildView)?.getParentView() ?: (mView as? ParentView))?.getActivity()
            ?.let {
                ActivityRouter.openActivityAndClosePrevent(it, AuthActivity::class.java)
                it.finish()
            }
    }

    protected fun getRootComponent(activity: Activity): RootComponent =
        (activity.application as TeacherNavigatopApp).getRootComponent()

    protected fun getContext(): Context = mView!!.getContext()
}