package v_aniskin.com.trucktaxi.application.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by root on 02.08.17.
 */
class DateMapper {

    companion object {

        private val DATE_TIME_FORMAT: String = "dd.MM.yyyy в HH:mm"
        private val PARSE_DATE_TIME_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"

        private val mDateFormatter = SimpleDateFormat(DATE_TIME_FORMAT)
        private val mDateParser = SimpleDateFormat(PARSE_DATE_TIME_FORMAT)

        fun mapDate(date: String): String {
            val parsedDate = mDateParser.parse(date)
            return mDateFormatter.format(parsedDate)
        }
    }
}