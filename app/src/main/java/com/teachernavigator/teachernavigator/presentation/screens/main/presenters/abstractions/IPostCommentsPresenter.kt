package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.PostCommentsView

/**
 * Created by lliepmah on 08.10.17
 */
interface IPostCommentsPresenter : ViewAttacher<PostCommentsView> {

    fun initPost(postId: Int, postTypeOrdinal: Int)
    fun refresh()

    fun sendComment(text: CharSequence)

}