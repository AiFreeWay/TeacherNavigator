package com.teachernavigator.teachernavigator.presentation.facades.abstractions

import com.teachernavigator.teachernavigator.domain.models.Post

/**
 * Created by root on 07.09.17.
 */
interface IPostControllerFacade {

    fun like(post: Post, callbak: IPostControllerFacadeCallback)
    fun dislike(post: Post, callbak: IPostControllerFacadeCallback)
    fun save(post: Post, callbak: IPostControllerFacadeCallback)
    fun subscribe(post: Post, callbak: IPostControllerFacadeCallback)
    fun complain(post: Post, callbak: IPostControllerFacadeCallback)

    fun openCommentsScreen(post: Post, callbak: IPostControllerFacadeCallback)
    fun openProfileScreen(post: Post, callbak: IPostControllerFacadeCallback)
    fun openPostDetailScreen(post: Post)
}