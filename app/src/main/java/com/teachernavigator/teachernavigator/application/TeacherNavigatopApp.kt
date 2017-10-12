package com.teachernavigator.teachernavigator.application

import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import com.example.root.androidtest.application.di.components.DaggerRootComponent
import com.example.root.androidtest.application.di.components.RootComponent
import com.example.root.androidtest.application.di.modules.RootModule
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.orhanobut.hawk.Hawk

/**
 * Created by root on 11.08.17
 */
class TeacherNavigatopApp : MultiDexApplication() {

    private lateinit var mRootComponent: RootComponent

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        Hawk.init(this).build()
        RxPaparazzo.register(this)
        mRootComponent = DaggerRootComponent.builder()
                .rootModule(RootModule(this))
                .build()
    }

    fun getRootComponent(): RootComponent = mRootComponent
}