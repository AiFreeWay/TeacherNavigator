package com.teachernavigator.teachernavigator.domain.interactors

import com.example.root.androidtest.application.utils.Logger
import com.teachernavigator.teachernavigator.BuildConfig
import com.teachernavigator.teachernavigator.data.repository.abstractions.IAuthRepository
import com.teachernavigator.teachernavigator.domain.interactors.abstractions.IAuthInteractor
import com.teachernavigator.teachernavigator.domain.models.Monade
import com.teachernavigator.teachernavigator.domain.models.SingUpData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by root on 17.08.17.
 */
class AuthInteractor @Inject constructor(private val mRepository: IAuthRepository) : IAuthInteractor {

    init {
        if (BuildConfig.DEBUG) Logger.logDebug("created INTERACTOR AuthInteractor")
    }

    override fun isAuth(): Observable<Boolean> =
            mRepository.isAuth()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())

    override fun singInViaVkontakte(): Observable<Monade> =
            mRepository.singInViaVkontakte()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun singInViaFacebook(): Observable<Monade> =
            mRepository.singInViaFacebook()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun singInViaTwitter(): Observable<Monade> =
            mRepository.singInViaTwitter()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun singInViaGooglePlus(): Observable<Monade> =
            mRepository.singInViaGooglePlus()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun singIn(login: String, password: String): Observable<Monade> =
            mRepository.singIn(login, password)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun singUp(singUpData: SingUpData): Observable<Monade> =
            mRepository.singUp(singUpData)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())

    override fun restorePassword(login: String): Observable<Monade> =
            mRepository.restorePassword(login)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
}