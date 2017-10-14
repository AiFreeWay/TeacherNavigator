package com.teachernavigator.teachernavigator.presentation.screens.auth.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log.d
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.presentation.screens.auth.activities.abstractions.AuthParentView
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RegistrationFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.RestorePasswordFragment
import com.teachernavigator.teachernavigator.presentation.screens.auth.fragments.abstractions.AuthView
import com.teachernavigator.teachernavigator.presentation.screens.auth.presenters.abstractions.IAuthPresenter
import com.teachernavigator.teachernavigator.presentation.screens.common.BasePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.MainActivity
import com.teachernavigator.teachernavigator.presentation.utils.ActivityRouter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.vk.sdk.VKSdk
import ru.terrakok.cicerone.Router
import javax.inject.Inject


/**
 * Created by root on 22.08.17
 */
@PerParentScreen
class AuthPresenter
@Inject
constructor(val router: Router,
            private val authInteractor: IAuthInteractor) : BasePresenter<AuthView>(),

        IAuthPresenter,
        GoogleApiClient.OnConnectionFailedListener,
        FacebookCallback<LoginResult> {

    override fun onConnectionFailed(p0: ConnectionResult) {
        d(javaClass.name, "-> Google Auth Error -> ${p0.errorMessage}")
    }


    private val googleApiClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
//                .requestServerAuthCode(mView!!.getContext().getString(R.string.google_client_id))
                .requestIdToken(mView!!.getContext().getString(R.string.google_client_id))
                .requestEmail()
                .build()

        GoogleApiClient.Builder(mView!!.getContext())
                .enableAutoManage(mView!!.getParentView().getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    private val twitterAuthClient by lazy { TwitterAuthClient() }
    private val callbackManager by lazy { CallbackManager.Factory.create() }

    private var callback: Callback<TwitterSession> = object : Callback<TwitterSession>() {
        override fun failure(exception: TwitterException?) = exception?.let {
            doOnError(it)
        } ?: Unit

        override fun success(result: Result<TwitterSession>?) =
                result?.data?.authToken?.token?.let { singInViaTwitterToken(it) } ?: Unit
    }

    private fun singInViaTwitterToken(token: String) =
            addDissposable(authInteractor.singInViaTwitter(token)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingIn, this::doOnError))

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.auth)
        (mView?.getParentView() as? AuthParentView)?.hightActionBar()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    override fun doOnError(error: Throwable) {
        super.doOnError(error)
        stopProgress()
        mView!!.showToast(getContext().getString(R.string.auth_error))
    }

    override fun singInViaVkontakte() {
        VKSdk.login(mView!!.getParentView().getActivity(), "email", "sex")
    }

    override fun singInViaFacebook(fmt: Fragment) = LoginManager.getInstance().let {
        it.registerCallback(callbackManager, this)
        it.logInWithReadPermissions(fmt, listOf("email", "public_profile"))
    }

    override fun singInViaTwitter() {
        val activity = mView?.getParentView()?.getActivity()
        if (activity != null) {
            twitterAuthClient.authorize(activity, callback)
        }
    }

    val RC_SIGN_IN = 3254

    override fun singInViaGooglePlus() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        mView?.getParentView()?.getActivity()?.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun singIn(login: String, password: String) =
            addDissposable(authInteractor.singIn(login, password)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingIn, this::doOnError))

    override fun openSingUpScreen() {
        router.navigateTo(RegistrationFragment.FRAGMENT_KEY)
    }

    override fun openRestorePasswordScreen() {
        router.navigateTo(RestorePasswordFragment.FRAGMENT_KEY)
    }

    private fun doOnSingIn(result: Monade) {
        stopProgress()
        if (!result.isError)
            ActivityRouter.openActivityAndClosePrevent(mView!!.getParentView().getActivity(), MainActivity::class.java)
    }

    override fun onSuccess(loginResult: LoginResult) =
            addDissposable(authInteractor.singInViaFacebook(loginResult.accessToken.token)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingIn, this::doOnError))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

//        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
//
//            override fun onError(error: VKError?) {
//                if (error != null) {
//
//                    d(javaClass.name, "-> onError -> ${error.errorMessage}")
//                }
//            }
//
//            override fun onResult(res: VKAccessToken?) {
//                d(javaClass.name, "-> onResult -> ${res?.accessToken ?: " no token"}")
//            }
//
//        })) else {
        when (requestCode) {
            RC_SIGN_IN -> {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                handleSignInResult(result)
            }
            twitterAuthClient.requestCode -> twitterAuthClient.onActivityResult(requestCode, resultCode, data)
            else -> callbackManager.onActivityResult(requestCode, resultCode, data)
        }
//        }
    }

    fun handleSignInResult(result: GoogleSignInResult) {

        d(javaClass.name, "-> result ->${result.signInAccount?.displayName}")
        d(javaClass.name, "-> result ->${result.signInAccount?.idToken}")

    }

    override fun onCancel() = Unit

    override fun onError(exception: FacebookException) {
        doOnError(exception)
    }

    private fun startProgress() = mView?.let {
        it.getParentView().startProgress()
        it.lockUi()
    }

    private fun stopProgress() = mView?.let {
        it.getParentView().stopProgress()
        it.unlockUi()
    }
}