package com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.presentation.screens.base.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.TapeListView

/**
 * Created by root on 18.08.17.
 */
interface ITapeListPresenter : ViewAttacher<TapeListView> {

    fun onPostClick(post: Post)
    fun setTapeType(tapeType: Int)
}