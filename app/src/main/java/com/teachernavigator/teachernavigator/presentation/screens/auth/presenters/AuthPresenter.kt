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
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
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
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
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

    companion object {
        const val RC_SIGN_IN = 3254
    }

    private val googleApiClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Scope(Scopes.PLUS_ME), Scope(Scopes.PLUS_LOGIN))
                .requestServerAuthCode(mView!!.getContext().getString(R.string.google_server_client_id), false)
                .requestProfile()

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

        override fun success(result: Result<TwitterSession>?) {
            val token = result?.data?.authToken?.token
            val secret = result?.data?.authToken?.secret

            if (token != null && secret != null) {
                singInViaTwitterToken(token, secret)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        mView?.getParentView()?.setToolbarTitle(R.string.auth)
        (mView?.getParentView() as? AuthParentView)?.hideActionBar()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposables.clear()
    }

    private fun singInViaTwitterToken(oauthToken: String, oauthTokenSecret: String) =
            addDissposable(authInteractor.singInViaTwitter(oauthToken, oauthTokenSecret)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingIn, this::doOnError))

    private fun singInViaVkToken(token: String) =
            addDissposable(authInteractor.singInViaVk(token)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingIn, this::doOnError))

    private fun singInViaGoogle(code: String) =
            addDissposable(authInteractor.singInViaGoogle(code)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingIn, this::doOnError))

    override fun doOnError(error: Throwable) {
        stopProgress()
        mView?.showToast(getContext().getString(R.string.auth_error))
    }

    override fun singInViaVkontakte() = mView?.let {
        VKSdk.login(it.getParentView().getActivity(), "email", "sex", "profile")
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

    override fun singInViaGooglePlus() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        mView?.getParentView()?.getActivity()?.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun singIn(login: String, password: String) =
            addDissposable(authInteractor.singIn(login, password)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingIn, this::doOnError))

    /* Common Success */
    private fun doOnSingIn(result: Monade) {
        stopProgress()
        if (!result.isError) {
            ActivityRouter.openActivityAndClosePrevent(mView!!.getParentView().getActivity(), MainActivity::class.java)
        }
    }

    override fun openSingUpScreen() {
        router.navigateTo(RegistrationFragment.FRAGMENT_KEY)
    }

    override fun openRestorePasswordScreen() {
        router.navigateTo(RestorePasswordFragment.FRAGMENT_KEY)
    }

    /* G+ Error */
    override fun onConnectionFailed(p0: ConnectionResult) {
        d(javaClass.name, "-> Google Auth Error -> ${p0.errorMessage}")
    }

    /* G+ Success */
    fun handleSignInResult(result: GoogleSignInResult) {
        result.signInAccount?.serverAuthCode?.let { singInViaGoogle(it) }

        d(javaClass.name, "-> result ->${result.signInAccount?.displayName}")
        d(javaClass.name, "-> result serverAuthCode ->${result.signInAccount?.serverAuthCode}")
    }

    /* Fb Success */
    override fun onSuccess(loginResult: LoginResult) =
            addDissposable(authInteractor.singInViaFacebook(loginResult.accessToken.token)
                    .doOnSubscribe { startProgress() }
                    .subscribe(this::doOnSingIn, this::doOnError))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        when (requestCode) {
            RC_SIGN_IN -> handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data))
            twitterAuthClient.requestCode -> twitterAuthClient.onActivityResult(requestCode, resultCode, data)
            else -> if (!callbackManager.onActivityResult(requestCode, resultCode, data)) {

                VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {

                    override fun onError(error: VKError?) {
                        this@AuthPresenter.doOnError(Error(error?.errorMessage ?: "Unknown error"))
                    }

                    override fun onResult(res: VKAccessToken?) {
                        res?.let { singInViaVkToken(it.accessToken) }
                    }

                })
            }
        }
//        }
    }

    override fun onCancel() = Unit

    /* Fb Error */
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