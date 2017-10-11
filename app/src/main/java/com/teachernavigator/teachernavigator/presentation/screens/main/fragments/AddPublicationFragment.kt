package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData
import com.miguelbcr.ui.rx_paparazzo2.entities.Response
import com.tbruyelle.rxpermissions2.RxPermissions
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.domain.models.Tag
import com.teachernavigator.teachernavigator.presentation.adapters.holders.TagVH
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.AddPublicationView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IAddPublicationPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fmt_add_publication.*
import kotlinx.android.synthetic.main.v_add_tag.*
import javax.inject.Inject

/**
 * Created by root on 20.09.17
 */

class AddPublicationFragment : BaseFragment(), AddPublicationView {

    companion object {
        val FRAGMENT_KEY = "add_publication_fragment"
    }

    private lateinit var mParentScreenComponent: ParentScreenComponent

    @Inject
    lateinit var presenter: IAddPublicationPresenter

    private val rxPermissions by lazy { RxPermissions(activity) }

    private lateinit var tagHolder1: TagVH
    private lateinit var tagHolder2: TagVH

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_add_publication, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fmtAddPublicationTvAddFile.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_resume), null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        presenter.attachView(this)
        tagHolder1 = TagVH(vAddTagVTag1, { addTag(it) })
        tagHolder2 = TagVH(vAddTagVTag2, { addTag(it) })

        cancelTags()
        vAddTagBtnCancel.setOnClickListener { cancelTags() }
        vAddTagBtnAdd.setOnClickListener { addTag() }

        fmtAddPublicationBtnPublic.setOnClickListener { publish() }
        fmtAddPublicationBtnPreview.setOnClickListener { preview() }
        fmtAddPublicationTvAddTag.setOnClickListener { showAddTags() }
        fmtAddPublicationTvAddFile.setOnClickListener { attachFile() }
        fmtAddPublicationVAddTag.setOnClickListener { cancelTags() }

        vAddTagEtTag.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.searchTags(text ?: "")
            }
        })
    }

    private fun attachFile() {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .map { if (!it) throw Error("Permission Denied") }
                .flatMap {
                    RxPaparazzo.single(this@AddPublicationFragment)
                            .usingFiles()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFileAttached, this::onAttachError)
    }

    private fun onAttachError(error: Throwable) {
        error.printStackTrace()
    }

    private fun onFileAttached(response: Response<AddPublicationFragment, FileData>) {
        val data = response.data()
        if (response.resultCode() == Activity.RESULT_OK && data != null) {
            fmtAddPublicationTvAddFile.text = data.filename
            presenter.filePath = data.file.path
            presenter.fileMime = data.mimeType
        }
    }

    override fun setTags(tags: List<String>) {
        fmtAddPublicationHvHashtags.setData(tags)
    }

    private fun addTag(tag: Tag) {
        presenter.addTag(tag.name)
        cancelTags()
    }

    private fun addTag() {
        presenter.addTag(vAddTagEtTag.text)
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

    private fun cancelTags() {
        vAddTagVTag1.visibility = View.INVISIBLE
        vAddTagVTag2.visibility = View.INVISIBLE
        vAddTagEtTag.setText(R.string.empty)

        TransitionManager.beginDelayedTransition(fmtAddPublicationVAddTag.parent as ViewGroup, Fade())
        fmtAddPublicationVAddTag.visibility = View.GONE
    }

    private fun showAddTags() {
        presenter.searchTags("")
        fmtAddPublicationVAddTag.visibility = View.VISIBLE
    }

    private fun inject() {
        mParentScreenComponent = DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(getParentView()))
                .build()
                .also { it.inject(this) }
    }

    private fun preview() =
            presenter.preview(fmtAddPublicationEtTitle.text, fmtAddPublicationEtText.text)

    private fun publish() =
            presenter.publish(fmtAddPublicationEtTitle.text, fmtAddPublicationEtText.text)

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

}