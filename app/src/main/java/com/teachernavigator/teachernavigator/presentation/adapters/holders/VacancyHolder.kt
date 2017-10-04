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
                    isMine: Boolean,
                    private val onProlongListener: OnProlongVacancyListener?,
                    private val onDeleteListener: OnDeleteVacancyListener?,
                    private val onResponseListener: OnResponseVacancyListener?,
                    private val onClickListener: OnClickVacancyListener?) : DefaultViewHolder<VacancyModel>(itemView) {

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
    private val vVacancyBtnResponse: Button = itemView.find(R.id.vVacancyBtnResponse)
    private val vVacancyRoot: View = itemView.find(R.id.vVacancyRoot)

    init {
        if (isMine) {
            vVacancyBtnDelete.setOnClickListener { onDelete() }
            vVacancyBtnProlong.setOnClickListener { onProlong() }
            vVacancyRoot.setOnClickListener { onClick()}
        } else {
            vVacancyBtnResponse.setOnClickListener { onResponse() }
        }

        vVacancyBtnResponse.visibility = if (isMine || onResponseListener == null) View.GONE else View.VISIBLE

        vVacancyTvRemains.visibility = if (isMine) View.VISIBLE else View.GONE
        vVacancyBtnDelete.visibility = if (isMine && onDeleteListener != null) View.VISIBLE else View.GONE
        vVacancyBtnProlong.visibility = if (isMine && onProlongListener != null) View.VISIBLE else View.GONE
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

    private fun onClick() = mVacancy?.let {
        onClickListener?.invoke(it)
    }

    private fun onProlong() = mVacancy?.let {
        onProlongListener?.invoke(it)
    }

    private fun onDelete() = mVacancy?.let {
        onDeleteListener?.invoke(it)
    }

    private fun onResponse() = mVacancy?.let {
        onResponseListener?.invoke(it)
    }

}

typealias OnProlongVacancyListener = (VacancyModel) -> Unit
typealias OnDeleteVacancyListener = (VacancyModel) -> Unit
typealias OnResponseVacancyListener = (VacancyModel) -> Unit
typealias OnClickVacancyListener = (VacancyModel) -> Unit