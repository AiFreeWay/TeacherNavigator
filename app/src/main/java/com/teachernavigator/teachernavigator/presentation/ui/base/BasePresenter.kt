package com.teachernavigator.teachernavigator.presentation.ui.base

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
abstract class BasePresenter<V : BaseView>(protected val mView: V) : LifecycleObserver {

    protected val mDisposables: CompositeDisposable = CompositeDisposable()

    init {
        mView.lifecycle.addObserver(this)
    }

    protected fun addDissposable(disposable: Disposable) {
        mDisposables.add(disposable)
    }

    protected fun doOnError(error: Throwable) {
        Logger.logError(error)
    }

    protected fun getRootComponent(activity: Activity) : RootComponent {
        return (activity.application as TeacherNavigatopApp).getRootComponent()
    }
}