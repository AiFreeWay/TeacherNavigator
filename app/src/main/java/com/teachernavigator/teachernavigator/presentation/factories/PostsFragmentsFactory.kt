package com.teachernavigator.teachernavigator.presentation.factories

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.PollHolder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.PostHolder
import com.teachernavigator.teachernavigator.presentation.facades.abstractions.IPostControllerFacade
import com.teachernavigator.teachernavigator.presentation.models.ViewPagerItemContainer
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.PostsListFragment
import com.teachernavigator.teachernavigator.presentation.utils.TapeStrategy

/**
 * Created by root on 17.08.17.
 */
class PostsFragmentsFactory {

    companion object {

        fun createItems(context: Context, facade: IPostControllerFacade): List<ViewPagerItemContainer> {
            val itemContainers = ArrayList<ViewPagerItemContainer>()

            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.last), createPostFragment(TapeStrategy.POSTS_TYPE_LAST, context, facade)))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.best), createPostFragment(TapeStrategy.POSTS_TYPE_BEST, context, facade)))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), createPostFragment(TapeStrategy.POSTS_TYPE_NEWS, context, facade)))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), createPollFragment(TapeStrategy.POSTS_TYPE_INTERVIEWS, context, facade)))
            return itemContainers
        }

        fun createForUnregisterUserItems(context: Context, facade: IPostControllerFacade): List<ViewPagerItemContainer> {
            val itemContainers = ArrayList<ViewPagerItemContainer>()
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.news), createPostFragment(TapeStrategy.POSTS_TYPE_NEWS, context, facade)))
            itemContainers.add(ViewPagerItemContainer(context.getString(R.string.interviews), createPollFragment(TapeStrategy.POSTS_TYPE_INTERVIEWS, context, facade)))
            return itemContainers
        }

        private fun addPostsType(tapeType: Int, fagment: Fragment): Fragment {
            val bundle = Bundle()
            bundle.putInt(PostsListFragment.POSTS_TYPE_KEY, tapeType)
            fagment.arguments = bundle
            return fagment
        }

        private fun createPostFragment(tapeType: Int, context: Context, facade: IPostControllerFacade): Fragment {
            val fragment = PostsListFragment.getInstance(PostHolder(context, facade))
            return addPostsType(tapeType, fragment)
        }

        private fun createPollFragment(tapeType: Int, context: Context, facade: IPostControllerFacade): Fragment {
            val fragment = PostsListFragment.getInstance(PollHolder(context, facade))
            return addPostsType(tapeType, fragment)
        }
    }
}