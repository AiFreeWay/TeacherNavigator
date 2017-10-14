package com.teachernavigator.teachernavigator.presentation.screens.tape.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.tape.fragments.abstractions.PostsSearchView
import com.teachernavigator.teachernavigator.presentation.screens.tape.presenters.abstractions.IPostSearchPresenter
import kotlinx.android.synthetic.main.fmt_post_search.*
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 14.10.17
 */

class PostsSearchFragment : BaseFragment(), PostsSearchView {

    companion object {
        val FRAGMENT_KEY = PostsSearchFragment::class.java.name!!
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var presenter: IPostSearchPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_post_search, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        presenter.attachView(this)

        fmtPostSearchBtnFind.setOnClickListener { presenter.navigateBack() }
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }

}
