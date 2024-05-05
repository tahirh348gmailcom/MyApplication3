package com.example.myapplication3.utils

import com.example.myapplication3.models.PushNotificationModel

class Constants {

    companion object {

        val SHOW_LOG = true
        const val SHOW_TOAST = true

        var MAX_DAYS = 90

        const val STATUS_SUCCESS = "success"
        const val SUCCESS = 200
        val CODE_401 = 401
        val PAGE_LIMIT = 10
        var PHONE_MIN = 8
        var PHONE_MAX = 15
        val FORCE_UPDATE = 308

        var CHAT_WITH: String? = null

        /*Booking related keys*/
        var CREATE_BOOKING_LAWYER_ID = ""
        var CREATE_BOOKING_COMMUNICATION_METHOD = ""
        var CREATE_BOOKING_SHORT_SUMMARY = ""
        var CREATE_BOOKING_CATEGORY = ""
        var CREATE_BOOKING_CONSULTATION_TYPE = ""
        var CREATE_BOOKING_DOCUMENT = ""
        var CREATE_BOOKING_AVAILABILITY_SLOT_ID = ""
        var CREATE_BOOKING_SLOTS_LIST : ArrayList<String> = ArrayList()
        var CREATE_BOOKING_START_TIME : Long = 0
        var CREATE_BOOKING_END_TIME : Long = 0

        const val BASE_IMAGE_URL = "https://mealmenydemo.s3.us-west-2.amazonaws.com/"
        const val TEMP_IMAGE = "https://pbs.twimg.com/media/FjU2lkcWYAgNG6d.jpg"
        const val tempWalletBalance = 1000000

        /*Local*/
        //var BASE_URL = "http://192.168.0.60:4452/api/"

        /*Staging*/
        //var BASE_URL = "https://constantinfo.net:4452/api/"

        /*Development*/
        //var BASE_URL = "https://constantinfo.net:4454/api/"

        /*EVT Staging*/
        /*var BASE_URL = "http://3.142.88.165:8501/api/"
        const val SOCKET = "http://3.142.88.165:8503"*/

        /*EVT Production Urls*/
/*        var BASE_URL = "https://api.evtapi.com/api/"
        const val SOCKET = "https://chat.evtapi.com"*/

        /*Dummy apis*/
        var BASE_URL = "https://dummy.restapiexample.com/api/v1/"

        var DOCS_EMBEDED_URL = "https://docs.google.com/gview?embedded=true&url="

        const val TYPE_USER = "CLIENT"
        const val TYPE_LAWYER = "LAWYER"
        const val SIGN_UP = "SIGN_UP"
        const val CHANGE_PHONE = "CHANGE_PHONE"
        const val FORGOT_PASSWORD = "FORGOT_PASSWORD"
        const val GOOGLE = "GOOGLE"
        const val LANGUAGE_EN = "LANGUAGE_EN"
        const val LANGUAGE_AR = "LANGUAGE_AR"
        const val ONLINE = "ONLINE"
        const val OFFLINE = "OFFLINE"
        const val AUDIO_CALL = "AUDIO_CALL"
        const val VIDEO_CALL = "VIDEO_CALL"
        const val WRITTEN_COMMUNICATION = "WRITTEN_COMMUNICATION"
        const val direct = "direct"
        const val reserve = "reserve"
        const val OLD = "OLD"
        const val CURRENT = "CURRENT"
        const val UPCOMMING = "UPCOMMING"
        const val cancel = "cancel"


        const val TEMP_DEVICE_TOKEN = "xyz"
        const val TEMP_URL = "https://www.google.com/"

        /*AWS*/
        val AWS_LOCATION_USERS = "users/"
        val IMAGE = "IMAGE"
        val VIDEO_MP4 = "VIDEO.MP4"
        val PDF = "PDF"

        const val COUNTRY = "country"
        val CITY = "city"

        /*Twilio*/
        var BUSY: Boolean = false
        var callTokenModel : PushNotificationModel.Data? = null
        var CALL_TYPE = ""
        var CALL_TYPE_INCOMING = "incoming"
        var CALL_TYPE_OUTGOING = "outgoing"
        var callAcceptWaitingTime : Long = 2 * 1000 * 60

        /*===========================================Intent Keys=============================================================*/
        val INTENT_KEY_DATA = "data"
        val INTENT_KEY_TYPE = "type"
        val INTENT_KEY_COUNTRY_CODE = "country_code"
        val INTENT_KEY_PHONE = "phone"
        val INTENT_KEY_FULL_NAME = "full_name"
        val INTENT_KEY_SOCIAL_ID = "social_id"
        val INTENT_KEY_MESSAGE = "message"
        val INTENT_KEY_BUTTON = "button"
        val INTENT_KEY_REQUEST_OTP_FAIL = "request_otp_fail"
        val INTENT_KEY_ID = "id"
        val INTENT_KEY_VIDEO_THUMBNAIL = "video_thumbnail"
        val INTENT_KEY_NOTIFICATION_DATA = "PATH"
        val INTENT_KEY_IS_NOTIFICATION = "is_notification"

        /*=====================Preference keys======================*/
        val PREFERENCE_KEY_IS_LOGGED_IN = "is_user_logged_in" + Utils.getAppName()
        val PREFERENCE_KEY_USER_DATA = "user_data" + Utils.getAppName()
        val PREFERENCE_KEY_USER_TOKEN = "user_token" + Utils.getAppName()
        val PREFERENCE_KEY_IS_TUTORIAL_VIEWED = "is_tutorial_viewed" + Utils.getAppName()
        val PREFERENCE_KEY_FCM_TOKEN = "fcm_token"+Utils.getAppName()

        val CONTENT_IMAGE = "image/jpg"
        val CONTENT_VIDEO = "video/mp4"
        val CONTENT_DOCUMENT = "doc"

        /*========================================BroadCast receiver keys==========================================================*/
        val BROADCAST_KEY_NOTIFICATION = "broadcast_key_notification"+Utils.getAppName()
        val BROADCAST_KEY_CALL_INVITATION_REJECTED_BY_OPPONENT = "broadcast_key_call_invitation_rejected_by_opponent"+Utils.getAppName()

        /*Notification type*/
        var NOTIFICATION_TYPE_BROADCAST = "BROADCAST"
    }
}