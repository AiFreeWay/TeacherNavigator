package v_aniskin.com.trucktaxi.application.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by root on 02.08.17.
 */
class DateMapper {

    companion object {

        val DATE_FORMAT: String = "dd-MM-yyyy"
        val DATE_TIME_FORMAT: String = "dd-MM-yyyy HH:mm"
        val TIME_FORMAT: String = "HH:mm"

        fun mapDate(date: Long): String {
            val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT)
            return dateFormat.format(Date(date*1000L))
        }

        fun mapDateTime(date: Long): String {
            val dateFormat: DateFormat = SimpleDateFormat(DATE_TIME_FORMAT)
            return dateFormat.format(Date(date*1000L))
        }

        fun mapTime(date: Long): String {
            val dateFormat: DateFormat = SimpleDateFormat(TIME_FORMAT)
            return dateFormat.format(Date(date*1000L))
        }
    }
}