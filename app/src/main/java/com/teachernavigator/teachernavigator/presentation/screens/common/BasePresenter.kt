package com.teachernavigator.teachernavigator.presentation.screens.common

import android.app.Activity
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import com.example.root.androidtest.application.di.components.RootComponent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.application.TeacherNavigatopApp
import com.teachernavigator.teachernavigator.data.cache.CacheController
import com.teachernavigator.teachernavigator.data.cache.CacheController.Companion.TOKEN_KEY
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.AuthActivity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

/**
 * Created by root on 11.08.17.
 */
abstract class BasePresenter<V : BaseView> : LifecycleObserver, ViewAttacher<V> {

    protected val mDisposables: CompositeDisposable = CompositeDisposable()
    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
        mView!!.lifecycle.addObserver(this)
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
            CacheController.removeData(TOKEN_KEY)

            ((mView as? ChildView)?.getParentView() ?: (mView as? ParentView))?.getActivity()?.let {
                ActivityRouter.openActivity(it, AuthActivity::class.java)
                it.finish()
            }
        }
    }

    protected fun getRootComponent(activity: Activity): RootComponent {
        return (activity.application as TeacherNavigatopApp).getRootComponent()
    }

    protected fun getContext(): Context = mView!!.getContext()
}