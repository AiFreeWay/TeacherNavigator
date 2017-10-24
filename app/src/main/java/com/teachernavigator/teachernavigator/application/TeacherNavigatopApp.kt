package com.teachernavigator.teachernavigator.application

import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.example.root.androidtest.application.di.components.DaggerRootComponent
import com.example.root.androidtest.application.di.components.RootComponent
import com.example.root.androidtest.application.di.modules.RootModule
import com.google.firebase.FirebaseApp
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.orhanobut.hawk.Hawk
import com.teachernavigator.teachernavigator.R
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.vk.sdk.VKSdk
import io.fabric.sdk.android.Fabric
import com.neovisionaries.ws.client.WebSocketFactory
import com.neovisionaries.ws.client.ProxySettings






/**
 * Created by root on 11.08.17
 */
class TeacherNavigatopApp : MultiDexApplication() {

    private lateinit var mRootComponent: RootComponent

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        Hawk.init(this).build()
        Fabric.with(this, Crashlytics())
        RxPaparazzo.register(this)

        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(
                        TwitterAuthConfig(getString(R.string.twitter_consumer_key), getString(R.string.twitter_consumer_secret)))
                .debug(true)
                .build()
        Twitter.initialize(config)

        VKSdk.initialize(this)

        mRootComponent = DaggerRootComponent.builder()
                .rootModule(RootModule(this))
                .build()
    }

    fun getRootComponent(): RootComponent = mRootComponent
}