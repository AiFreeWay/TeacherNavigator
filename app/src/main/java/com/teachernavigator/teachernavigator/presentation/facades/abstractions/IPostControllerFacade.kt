package com.teachernavigator.teachernavigator.presentation.facades.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post

/**
 * Created by root on 07.09.17.
 */
interface IPostControllerFacade {

    fun like(vote: Boolean, post: Post, callback: IPostControllerFacadeCallback)
    fun save(post: Post, callback: IPostControllerFacadeCallback)
    fun subscribe(post: Post, callback: IPostControllerFacadeCallback)
    fun complain(post: Post, callback: IPostControllerFacadeCallback)

    fun openCommentsScreen(post: Post, callback: IPostControllerFacadeCallback)
    fun openProfileScreen(post: Post, callback: IPostControllerFacadeCallback)
    fun openPostDetailScreen(post: Post)
}