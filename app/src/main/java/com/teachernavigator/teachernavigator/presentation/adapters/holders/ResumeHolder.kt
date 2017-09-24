package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ResumeModel
import com.teachernavigator.teachernavigator.presentation.utils.ImageLoader
import com.teachernavigator.teachernavigator.presentation.utils.find
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by lliepmah on 24.09.17
 */
@HolderBuilder(R.layout.v_resume)
class ResumeHolder(itemView: View,
                   private val onProlongListener: OnProlongResumeListener,
                   private val onDeleteListener: OnDeleteResumeListener) : DefaultViewHolder<ResumeModel>(itemView) {

    private val vResumeIvAvatar: ImageView = itemView.find(R.id.vResumeIvAvatar)
    private val vResumeTvName: TextView = itemView.find(R.id.vResumeTvName)
    private val vResumeTvCareerObjective: TextView = itemView.find(R.id.vResumeTvCareerObjective)
    private val vResumeIvFile: ImageView = itemView.find(R.id.vResumeIvFile)
    private val vResumeTvDistrictCouncil: TextView = itemView.find(R.id.vResumeTvDistrictCouncil)
    private val vResumeTvSalary: TextView = itemView.find(R.id.vResumeTvSalary)
    private val vResumeTvSuitableVacancies: TextView = itemView.find(R.id.vResumeTvSuitableVacancies)
    private val vResumeTvRemains: TextView = itemView.find(R.id.vResumeTvRemains)

    private val vResumeBtnDelete: Button = itemView.find(R.id.vResumeBtnDelete)
    private val vResumeBtnProlong: Button = itemView.find(R.id.vResumeBtnProlong)

    private var mResume: ResumeModel? = null

    init {
        vResumeBtnDelete.setOnClickListener { onDelete() }
        vResumeBtnProlong.setOnClickListener { onProlong() }
    }

    override fun bind(resume: ResumeModel?) {
        mResume = resume

        resume?.let {
            if (!it.userAvatar.isBlank()) {
                ImageLoader.load(itemView.context, it.userAvatar, vResumeIvAvatar)
            } else {
                vResumeIvAvatar.setImageResource(R.drawable.ic_avatar)
            }

            vResumeTvName.text = it.userName
            vResumeTvCareerObjective.text = it.careerObjective
            vResumeTvDistrictCouncil.text = it.districtCouncil
            vResumeTvSalary.text = "???"

            vResumeTvSuitableVacancies.visibility = if (it.isMine) View.VISIBLE else View.GONE
            vResumeTvRemains.visibility = if (it.isMine) View.VISIBLE else View.GONE
            vResumeBtnDelete.visibility = if (it.isMine) View.VISIBLE else View.GONE
            vResumeBtnProlong.visibility = if (it.isMine) View.VISIBLE else View.GONE


            vResumeTvSuitableVacancies.text = "Подходящих вакансий: 118"
            vResumeTvRemains.text = it.daysRemains
        }
    }

    private fun onProlong() = mResume?.let {
        onProlongListener(it)
    }

    private fun onDelete() = mResume?.let {
        onDeleteListener(it)
    }

}

typealias OnProlongResumeListener = (ResumeModel) -> Unit
typealias OnDeleteResumeListener = (ResumeModel) -> Unit