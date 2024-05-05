package com.example.myapplication3.models

import android.text.format.DateUtils
import com.example.myapplication3.models.BaseModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


data class NotificationListModel(

    var page: Int? = null,
    var perPage: Int? = null,
    var count: Int? = null,
    var hasMore: Boolean? = null,
    var notifications: ArrayList<NotificationDataModel> = arrayListOf()

    ) : BaseModel() {

}

data class NotificationDataModel(

    var _id: String? = null,
    var isRead: Boolean? = null,
    var isDeleted: Boolean? = null,
    var title: String? = null,
    var message: String? = null,
    var user: String? = null,
    var created: String? = null,
    var updated: String? = null,
    var type: String? = null,
    var underscoreV: Int? = null,

) : BaseModel() {

    fun relativeTime(): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val timeAgoString =
            created?.let { inputFormat.parse(it) }
                ?.let { DateUtils.getRelativeTimeSpanString(it.time).toString() }
        if (timeAgoString?.startsWith("0") == true) {
            return ""
        }
        return timeAgoString

    }
}