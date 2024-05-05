package com.example.myapplication3.utils

import org.joda.time.format.DateTimeFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {

        @JvmStatic
        val DATE_FORMAT_MMM_dd_yyyy = "MMM dd, yyyy" //Oct 06, 2021

        @JvmStatic
        val DATE_FORMAT_EEE_MMM_dd_yyyy = "EEE, MMM dd yyyy" //Oct 06, 2021

        const val DATE_FORMAT_h_mm_aa = "h:mm aa" //1:38 PM

        const val DATE_FORMAT_h = "h" //4

        const val DATE_FORMAT_mm = "mm" //05

        const val DATE_FORMAT_aa = "aa" //PM

        @JvmField
        val DATE_FORMAT = DATE_FORMAT_MMM_dd_yyyy

        @JvmField
        val DATE_FORMAT_DD = "dd" //16

        @JvmField
        val DATE_FORMAT_MM = "MMM" //Oct

        @JvmField
        val TIME_FORMAT = DATE_FORMAT_h_mm_aa

        @JvmStatic
        val DATE_FORMAT_yyyy_MM_dd = "yyyy-MM-dd" //2021-09-02 2021-10-25

        @JvmField
        val DATE_FORMAT_MONGO_DB = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        @JvmStatic
        val DATE_FORMAT_hh_mm = "HH:mm"

        @JvmField
        val DATE_FORMAT_MMM_dd_yyyy_h_mm_aa = "MMM dd, yyyy h:mm aa" //Oct 06, 2021 1:38 PM

        @JvmField
        val DATE_FORMAT_MMM_dd_yyyy__h_mm_aa = "MMM dd, yyyy | h:mm aa" //Oct 06, 2021 | 1:38 PM

        const val DATE_FORMAT_hh_mm_a_mm_dd_yyyy = "hh:mm a, MMM dd, yyyy"
        const val DATE_FORMAT_hh_mm_a_mm_dd = "hh:mm a, MMM dd"
        val DATE_FORMAT_yyyy = "yyyy" //2021
        const val DATE_FORMAT_dd_mm_yyyy_hh_mm_a = "dd MMM yyyy hh:mm aa"

        @JvmStatic
        fun getDate(dateTime: String?, outputFormat: String): String {
              return  getDate(dateTime, DATE_FORMAT_MONGO_DB, outputFormat)
        }

        @JvmStatic
        fun getDate(timeStamp: Long, dateFormat: String): String {
            var result = ""
            if (timeStamp > 0) {
                result = DateTimeFormat.forPattern(dateFormat).print(timeStamp)
            }
            return result
        }

        @JvmStatic
        fun getDate(dateTime: String?, inputFormat: String, outputFormat: String): String {
            if (dateTime != null) {
                try {
                    val format = SimpleDateFormat(inputFormat, Locale.ENGLISH)
                    format.timeZone = TimeZone.getTimeZone("UTC")
                    val date = format.parse(dateTime)
                    return DateTimeFormat.forPattern(outputFormat).print(date.time)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            return  ""
        }

        /*Get calender from string date*/
        fun getCalenderFromDate(date: String, inputFormat: String): Calendar? {
            var calendar: Calendar? = null
            try {
                val df = SimpleDateFormat(inputFormat)
                calendar = Calendar.getInstance()
                calendar!!.time = df.parse(date)
                calendar.set(Calendar.HOUR_OF_DAY, 2)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return calendar
        }

        /*Get calender from string date*/
        fun getCalenderFromDate(timeStamp: Long): Calendar? {
            var date = getDate(timeStamp, DATE_FORMAT)
            var calendar: Calendar? = null
            try {
                val df = SimpleDateFormat(DATE_FORMAT)
                calendar = Calendar.getInstance()
                calendar!!.time = df.parse(date)
                calendar.set(Calendar.HOUR_OF_DAY, 2)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return calendar
        }

        fun getTimeStampLong(): Long {
            return System.currentTimeMillis() / 1000
        }

        @JvmStatic
        fun getDate(calender: Calendar, outputFormat: String): String {
            var result = ""
            if (calender != null) {
                try {
                    val sdf = SimpleDateFormat(outputFormat, Locale.ENGLISH)
                    result = sdf.format(calender.time)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            return  result
        }
    }
}