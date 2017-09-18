package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsListFragment
import com.teachernavigator.teachernavigator.presentation.utils.TapeStrategy

/**
 * Created by root on 17.08.17.
 */
class PostsFragmentsFactory {

    companion object {

        fun createItems(context: Context): List<ViewPagerItemContainer> {
            val itemContainers = ArrayList<ViewPagerItemContainer>()
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.last), addPostsType(TapeStrategy.POSTS_TYPE_LAST, PostsListFragment())))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.best), addPostsType(TapeStrategy.POSTS_TYPE_BEST, PostsListFragment())))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), addPostsType(TapeStrategy.POSTS_TYPE_NEWS, PostsListFragment())))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), addPostsType(TapeStrategy.POSTS_TYPE_INTERVIEWS, PostsListFragment())))
            return itemContainers
        }

        fun createForUnregisterUserItems(context: Context): List<ViewPagerItemContainer> {
            val itemContainers = ArrayList<ViewPagerItemContainer>()
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), addPostsType(TapeStrategy.POSTS_TYPE_NEWS, PostsListFragment())))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), addPostsType(TapeStrategy.POSTS_TYPE_INTERVIEWS, PostsListFragment())))
            return itemContainers
        }

        private fun addPostsType(tapeType: Int, fagment: Fragment): Fragment {
            val bundle = Bundle()
            bundle.putInt(PostsListFragment.POSTS_TYPE_KEY, tapeType)
            fagment.arguments = bundle
            return fagment
        }
    }
}