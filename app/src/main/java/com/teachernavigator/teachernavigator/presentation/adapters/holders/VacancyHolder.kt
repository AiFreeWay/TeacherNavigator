package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.VacancyModel
import com.teachernavigator.teachernavigator.presentation.utils.find
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by lliepmah on 24.09.17
 */
@HolderBuilder(R.layout.v_vacancy)
class VacancyHolder(itemView: View,
                    val onProlongListener: OnProlongListener,
                    val onDeleteListener: OnDeleteListener) : DefaultViewHolder<VacancyModel>(itemView) {

    private var mVacancy: VacancyModel? = null

    private val vVacancyTvOrganization: TextView = itemView.find(R.id.vVacancyTvOrganization)
    private val vVacancyTvVacancy: TextView = itemView.find(R.id.vVacancyTvVacancy)
    private val vVacancyTvCity: TextView = itemView.find(R.id.vVacancyTvCity)
    private val vVacancyTvSalary: TextView = itemView.find(R.id.vVacancyTvSalary)
    private val vVacancyTvTypeOfEmployment: TextView = itemView.find(R.id.vVacancyTvTypeOfEmployment)
    private val vVacancyTvResponsibility: TextView = itemView.find(R.id.vVacancyTvResponsibility)
    private val vVacancyTvRemains: TextView = itemView.find(R.id.vVacancyTvRemains)
    private val vVacancyBtnDelete: Button = itemView.find(R.id.vVacancyBtnDelete)
    private val vVacancyBtnProlong: Button = itemView.find(R.id.vVacancyBtnProlong)

    init {
        vVacancyBtnDelete.setOnClickListener { onDelete() }
        vVacancyBtnProlong.setOnClickListener { onProlong() }
    }

    override fun bind(vacancy: VacancyModel?) {
        mVacancy = vacancy

        vVacancyTvOrganization.text = vacancy?.organization ?: ""
        vVacancyTvVacancy.text = vacancy?.vacancy ?: ""
        vVacancyTvCity.text = vacancy?.city ?: ""
        vVacancyTvSalary.text = vacancy?.salary ?: ""
        vVacancyTvTypeOfEmployment.text = vacancy?.typeOfEmployment ?: ""
        vVacancyTvResponsibility.text = vacancy?.responsibility ?: ""
        vVacancyTvRemains.text = vacancy?.daysRemains ?: ""

    }

    private fun onProlong() = mVacancy?.let {
        onProlongListener(it)
    }

    private fun onDelete() = mVacancy?.let {
        onProlongListener(it)
    }

}

typealias OnProlongListener = (VacancyModel) -> Unit
typealias OnDeleteListener = (VacancyModel) -> Unit