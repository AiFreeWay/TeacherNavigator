package com.example.root.androidtest.application.utils

import android.util.Log
import com.teachernavigator.teachernavigator.BuildConfig
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by root on 08.08.17.
 */
class Logger {

    companion object {

        private val LOG_TAG: String = "++++"
        private val STRING_WRITER: StringWriter = StringWriter()
        private val PRINT_WRITER : PrintWriter = PrintWriter(STRING_WRITER)

        fun logError(e: Throwable) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace(PRINT_WRITER)
                PRINT_WRITER.flush()
                Log.e(LOG_TAG, STRING_WRITER.toString())
            }
        }

        fun logDebug(message: String?) {
            if (BuildConfig.DEBUG) Log.d(LOG_TAG, message)
        }

        fun testLog(message: String) {
            Log.d(LOG_TAG, "TEST_LOG: "+message)
        }
    }
}