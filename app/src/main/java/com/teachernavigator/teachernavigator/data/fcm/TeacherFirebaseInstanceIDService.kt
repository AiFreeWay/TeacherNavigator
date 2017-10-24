package com.teachernavigator.teachernavigator.data.fcm

import com.google.firebase.iid.FirebaseInstanceIdService
import com.teachernavigator.teachernavigator.application.TeacherNavigatopApp


/**
 * Created by Arthur Korchagin on 24.10.17
 */
class TeacherFirebaseInstanceIDService : FirebaseInstanceIdService() {

    private val mainRepository by lazy { (application as TeacherNavigatopApp).getRootComponent().provideMainRepository() }

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        mainRepository.updateFCMToken()
    }
}
