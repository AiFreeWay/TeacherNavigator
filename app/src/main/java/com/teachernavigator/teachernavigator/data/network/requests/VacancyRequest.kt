package com.teachernavigator.teachernavigator.data.network.requests

/**
 * Created by lliepmah on 22.09.17
 */
data class VacancyRequest(val name_of_the_organization: String,
                          val vacancy: String,
                          val amount_of_wages: Int,
                          val city: String,
                          val experience: String,
                          val type_of_employment: Int,
                          val responsibility: String,
                          val type_of_scool: Int)
