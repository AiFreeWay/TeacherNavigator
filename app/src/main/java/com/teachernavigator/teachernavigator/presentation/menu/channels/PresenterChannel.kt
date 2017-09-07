package com.teachernavigator.teachernavigator.presentation.menu.channels

import com.teachernavigator.teachernavigator.presentation.models.MenuData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

/**
 * Created by root on 24.08.17.
 */
class PresenterChannel : IPresenterChannel {

    private lateinit var mInHolderFromPresenter: PublishSubject<MenuData<*>>
    private lateinit var mInPresenterFromHolder: PublishSubject<MenuData<*>>

    fun setSubjects(inHolderFromPresenter: PublishSubject<MenuData<*>>, inPresenterFromHolder: PublishSubject<MenuData<*>>) {
        mInHolderFromPresenter = inHolderFromPresenter
        mInPresenterFromHolder = inPresenterFromHolder
    }

    override fun getOutputChannel(): Observer<MenuData<*>> = mInHolderFromPresenter

    override fun getInputChannel(): Observable<MenuData<*>> = mInPresenterFromHolder
}