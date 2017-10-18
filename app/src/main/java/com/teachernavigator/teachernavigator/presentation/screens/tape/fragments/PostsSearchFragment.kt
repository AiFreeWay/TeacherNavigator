package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments

import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.adapters.holders.TagVH
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsSearchView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostSearchPresenter
import com.teachernavigator.teachernavigator.presentation.utils.argNullable
import kotlinx.android.synthetic.main.fmt_post_search.*
import kotlinx.android.synthetic.main.v_add_tag.*
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 14.10.17
 */

class PostsSearchFragment : BaseFragment(), PostsSearchView {


    companion object {
        val FRAGMENT_KEY = PostsSearchFragment::class.java.name!!

        const val POSTS_SOURCE_KEY = "posts_source"
    }

    private lateinit var tagHolder1: TagVH
    private lateinit var tagHolder2: TagVH

    @Inject
    lateinit var presenter: IPostSearchPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_post_search, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)
        presenter.attachView(this)
        presenter.setSource(argNullable(POSTS_SOURCE_KEY))

        tagHolder1 = TagVH(vAddTagVTag1, { addTag(it) })
        tagHolder2 = TagVH(vAddTagVTag2, { addTag(it) })

        cancelTags()
        vAddTagBtnCancel.setOnClickListener { cancelTags() }
        vAddTagBtnAdd.setOnClickListener { addTag() }

        fmtPostSearchBtnFind.setOnClickListener { search() }
        fmtPostSearchTvAddTag.setOnClickListener { showAddTags() }
        fmtPostSearchVAddTag.setOnClickListener { cancelTags() }
    }

    fun search() {

        // TODO OMG Remove it!
        val publicationsContent = when {
            fmtPostSearchChbOnlyTextPublication.isSelected -> true to false
            fmtPostSearchChbWithDocumentPublication.isSelected -> false to true
            else -> true to true
        }

        presenter.performSearch(fmtPostSearchEtSearch.text, publicationsContent)
    }

    override fun setChecked(publicationsContent: Pair<Boolean, Boolean>) =
            when (publicationsContent) {
                true to true -> fmtPostSearchChbAnyPublication.isSelected = true
                true to false -> fmtPostSearchChbOnlyTextPublication.isSelected = true
                false to true -> fmtPostSearchChbWithDocumentPublication.isSelected = true
                else -> Unit
            }

    override fun setText(text: CharSequence) {
        fmtPostSearchEtSearch.setText(text)
    }

    private fun cancelTags() {
        vAddTagVTag1.visibility = View.INVISIBLE
        vAddTagVTag2.visibility = View.INVISIBLE
        vAddTagEtTag.setText(R.string.empty)

        TransitionManager.beginDelayedTransition(fmtPostSearchVAddTag.parent as ViewGroup, Fade())
        fmtPostSearchVAddTag.visibility = View.GONE
    }

    private fun showAddTags() {
        presenter.searchTags("")
        fmtPostSearchVAddTag.visibility = View.VISIBLE
    }

    private fun addTag() {
        presenter.addTag(vAddTagEtTag.text)
        cancelTags()
    }

    private fun addTag(tag: Tag) {
        presenter.addTag(tag.name)
        cancelTags()
    }

    override fun setSearchTags(tags: List<Tag>) {
        vAddTagVTag1.visibility = if (tags.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        vAddTagVTag2.visibility = if (tags.size > 1) View.VISIBLE else View.INVISIBLE

        if (tags.isNotEmpty()) {
            tagHolder1.bind(tags[0])
        }

        if (tags.size > 1) {
            tagHolder2.bind(tags[1])
        }
    }

    override fun setTags(tags: List<String>) {
        fmtPostSearchTvHashtags.setData(tags)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }

}
