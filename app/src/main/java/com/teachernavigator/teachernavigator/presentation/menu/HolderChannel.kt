package com.teachernavigator.teachernavigator.presentation.menu

import com.teachernavigator.teachernavigator.presentation.menu.abstractions.IHolderChannel
import com.teachernavigator.teachernavigator.presentation.models.MenuData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

/**
 * Created by root on 24.08.17.
 */
class HolderChannel : IHolderChannel {

    private lateinit var mInHolderFromPresenter: PublishSubject<MenuData<*>>
    private lateinit var mInPresenterFromHolder: PublishSubject<MenuData<*>>

    fun setSubjects(inHolderFromPresenter: PublishSubject<MenuData<*>>, inPresenterFromHolder: PublishSubject<MenuData<*>>) {
        this.mInHolderFromPresenter = inHolderFromPresenter
        this.mInPresenterFromHolder = inPresenterFromHolder
    }

    override fun getOutputChannel(): Observer<MenuData<*>> = mInPresenterFromHolder

    override fun getInputChannel(): Observable<MenuData<*>> = mInHolderFromPresenter
}