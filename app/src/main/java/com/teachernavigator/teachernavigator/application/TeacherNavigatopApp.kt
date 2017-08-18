package com.teachernavigator.teachernavigator.application

import android.app.Application
import com.example.root.androidtest.application.di.components.DaggerRootComponent
import com.example.root.androidtest.application.di.components.RootComponent
import com.example.root.androidtest.application.di.modules.RootModule
import com.squareup.leakcanary.LeakCanary

/**
 * Created by root on 11.08.17.
 */
class TeacherNavigatopApp :Application() {

    private lateinit var mRootComponent: RootComponent

    override fun onCreate() {
        super.onCreate()
        mRootComponent = DaggerRootComponent.builder()
                .rootModule(RootModule(this))
                .build()
        LeakCanary.install(this)
    }

    fun getRootComponent(): RootComponent {
        return mRootComponent
    }
}