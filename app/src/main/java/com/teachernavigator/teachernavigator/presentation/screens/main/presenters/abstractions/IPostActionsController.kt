package com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions

import com.teachernavigator.teachernavigator.presentation.models.ChoiceModel
import com.teachernavigator.teachernavigator.presentation.models.CommentModel
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.ViewAttacher
import com.teachernavigator.teachernavigator.presentation.screens.info.fragments.abstractions.PostActionsView

/**
 * Created by lliepmah on 08.10.17
 */
interface IPostActionsController : ViewAttacher<PostActionsView> {

    fun onLike(post: PostModel)
    fun onDislike(post: PostModel)
    fun onComments(post: PostModel)
    fun onSave(post: PostModel)
    fun onSubscribe(post: PostModel)
    fun onSubscribe(comment: CommentModel)
    fun onReadMore(post: PostModel)
    fun onComplain(post: PostModel)
    fun onOpenProfile(post: PostModel)
    fun onOpenProfile(comment: CommentModel)
    fun onPollVote(post: PostModel, choiceModel: ChoiceModel)
    fun onOpenFile(url: String)

}