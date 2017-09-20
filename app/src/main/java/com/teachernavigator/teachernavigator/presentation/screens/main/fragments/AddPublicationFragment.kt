package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Post
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AddPublicationView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtAddPublicationPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IAddPublicationPresenter

/**
 * Created by root on 20.09.17.
 */
class AddPublicationFragment : BaseFragment(), AddPublicationView {

    @BindView(R.id.fmt_add_publication_et_title)
    lateinit var mEtTitle: EditText
    @BindView(R.id.fmt_add_publication_et_text)
    lateinit var mEtText: EditText
    @BindView(R.id.fmt_add_publication_btn_public)
    lateinit var mBtnPublic: Button
    @BindView(R.id.fmt_add_publication_btn_preview)
    lateinit var mBtnPreview: Button

    companion object {
        val FRAGMENT_KEY = "add_publication_fragment"
    }

    private val mPresenter: IAddPublicationPresenter = FmtAddPublicationPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_add_publication, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        mBtnPublic.setOnClickListener { mPresenter.doPublic(getPost()) }
        mBtnPreview.setOnClickListener { mPresenter.preview(getPost()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    fun getPost(): Post {
        val post = Post()
        post.title = mEtTitle.text.toString()
        post.text = mEtText.text.toString()
        return post
    }
}