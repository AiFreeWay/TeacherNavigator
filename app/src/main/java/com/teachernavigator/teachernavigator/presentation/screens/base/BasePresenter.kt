package com.teachernavigator.teachernavigator.presentation.screens.base

import android.app.Activity
import android.arch.lifecycle.LifecycleObserver
import com.example.root.androidtest.application.di.components.RootComponent
import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.application.TeacherNavigatopApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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
        Logger.logError(error)
    }

    protected fun getRootComponent(activity: Activity) : RootComponent {
        return (activity.application as TeacherNavigatopApp).getRootComponent()
    }
}