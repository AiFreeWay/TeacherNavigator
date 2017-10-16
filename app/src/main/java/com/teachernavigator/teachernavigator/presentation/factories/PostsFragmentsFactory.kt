package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import android.os.Bundle
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.PostsSource
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsListFragment

/**
 * Created by root on 17.08.17
 */
object PostsFragmentsFactory {

    fun createMyItems(context: Context, postsSource: PostsSource): List<ViewPagerItemContainer> {
        val itemContainers = ArrayList<ViewPagerItemContainer>()
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), createPostFragment(PostType.news, postsSource)))
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.last), createPostFragment(PostType.post, postsSource)))
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.important_to_know), createPostFragment(PostType.importantinfo, postsSource)))
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), createPostFragment(PostType.poll, postsSource)))
        return itemContainers
    }

    fun createItems(context: Context): List<ViewPagerItemContainer> {
        val itemContainers = ArrayList<ViewPagerItemContainer>()
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), createPostFragment(PostType.news)))
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.last), createPostFragment(PostType.post)))
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.best), createPostFragment(PostType.post, PostsSource.Best)))
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), createPostFragment(PostType.poll)))
        return itemContainers
    }

    fun createForUnregisterUserItems(context: Context): List<ViewPagerItemContainer> {
        val itemContainers = ArrayList<ViewPagerItemContainer>()
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), createPostFragment(PostType.news)))
        itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), createPostFragment(PostType.poll)))
        return itemContainers
    }


    private fun createPostFragment(type: PostType, source: PostsSource = PostsSource.Common) =
            PostsListFragment().apply {
                arguments = Bundle().apply {
                    putInt(PostsListFragment.POST_TYPE_KEY, type.ordinal)
                    putInt(PostsListFragment.POSTS_SOURCE_KEY, source.ordinal)
                }
            }

}