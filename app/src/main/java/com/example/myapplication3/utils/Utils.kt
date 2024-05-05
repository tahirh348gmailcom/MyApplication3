package com.example.myapplication3.utils

import android.app.*
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.media.ThumbnailUtils
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.example.myapplication3.BuildConfig
import com.example.myapplication3.R
import com.example.myapplication3.BaseApplication
import com.example.myapplication3.data.models.*
import com.example.myapplication3.interfaces.IDialog
import com.example.myapplication3.models.MyExtraData
import com.example.myapplication3.models.TempModel
import com.example.myapplication3.models.UserModel
import com.example.myapplication3.ui.activities.MainActivity
import java.io.File

class Utils {

    companion object {


        /*This method is use to show log*/
        @JvmStatic
        fun log(tag: String, value: String) {
            if (Constants.SHOW_LOG) {

                if (value.length > 4000) {
                    Log.e(tag, "sb.length = " + value.length)
                    val chunkCount: Int = value.length / 4000 // integer division
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= value.length) {
                            Log.e(
                                tag,
                                "chunk " + i + " of " + chunkCount + ":" + value.substring(4000 * i)
                            )
                        } else {
                            Log.e(
                                tag,
                                "chunk $i of $chunkCount:" + value.substring(
                                    4000 * i,
                                    max
                                )
                            )
                        }
                    }
                }else
                {
                    Log.e(tag, value)
                }
            }
        }

        /* Check internet connectivity */
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            } else {
                toast(context.resources.getString(R.string.no_network_message), context)
                return false
            }
        }

        /*This method is use to show toast*/
        fun toast(message: String, context: Context) {
            if (Constants.SHOW_TOAST) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        /*This method is use to show toast*/
        fun toastLong(message: String, context: Context) {
            if (Constants.SHOW_TOAST) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }

        fun getToken(): String? {
            return getStringDataFromPreferences(Constants.PREFERENCE_KEY_USER_TOKEN)
        }

        fun getLoginStatus(): Boolean {
            return getBooleanDataFromPreferences(Constants.PREFERENCE_KEY_IS_LOGGED_IN)
        }


        /*This method will return fcm device token*/
        fun getFcmToken(): String {
            var token = getStringDataFromPreferences(Constants.PREFERENCE_KEY_FCM_TOKEN)
            if(StringUtility.validateString(token))
            {
                return token
            }else {
                return "abc"
            }
        }



        /*This method is use to hide or close keyboard*/
        fun hideKeyboard(activity: Activity?) {
            var view = activity!!.getWindow().getCurrentFocus();
            var imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } else {
                imm.hideSoftInputFromWindow(
                    if (null == activity.getCurrentFocus()) null else activity.getCurrentFocus()!!
                        .getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

        fun onInfoWithResult(
            message: String,
            iDialog: IDialog,
            tag: Int,
            positiveButtonText: String,
            negativeButtonText: String,
            extraData: MyExtraData,
            title: String,
            cancellable: Boolean,
            activity: Activity
        ) {
            hideKeyboard(activity)
            var myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(activity)
            myAlertDialog.setCancelable(cancellable)
            myAlertDialog.setMessage(message)
            if (title != null && StringUtility.validateString(title)) {
                myAlertDialog.setTitle(title)
            }

            myAlertDialog.setPositiveButton(positiveButtonText)
            { dialog, which ->
                iDialog.onDialogClick(true, tag, extraData)
            }

            if (negativeButtonText != null && StringUtility.validateString(negativeButtonText)) {
                myAlertDialog.setNegativeButton(negativeButtonText)
                { dialog, which ->
                    iDialog.onDialogClick(false, tag, extraData)
                }
            }

            myAlertDialog.show()

        }

        /*============================    Shared Preferences   =====================================*/

        fun putStringDataInPreferences(key: String, value: String?) {
            PreferenceManager.getDefaultSharedPreferences(BaseApplication.mContext).edit().putString(
                key,
                value
            ).apply()
        }

        fun getStringDataFromPreferences(key: String): String {
            return PreferenceManager.getDefaultSharedPreferences(BaseApplication.mContext).getString(
                key,
                ""
            )!!
        }

        fun putBooleanDataInPreferences(key: String, value: Boolean?) {
            PreferenceManager.getDefaultSharedPreferences(BaseApplication.mContext).edit().putBoolean(
                key,
                value!!
            ).apply()
        }

        fun getBooleanDataFromPreferences(key: String): Boolean {
            return PreferenceManager.getDefaultSharedPreferences(BaseApplication.mContext).getBoolean(
                key,
                false
            )
        }

        fun clearPreferences() {
            PreferenceManager.getDefaultSharedPreferences(BaseApplication.mContext).edit().clear()
                .apply()
        }

        /*-------------------------------------------------------------------------------------------------*/

        fun getAppName(): String {
            return "_" + BuildConfig.APPLICATION_ID
        }

        fun openLicence(link: String, activity: Activity)
        {
            if(link.endsWith(".jpg") || link.endsWith(".png")) {
                openUrl(Constants.BASE_IMAGE_URL+link, activity)
            }else
            {
                openUrl(Constants.DOCS_EMBEDED_URL+Constants.BASE_IMAGE_URL+link, activity)
            }
        }

        fun openUrl(url: String, activity: Activity) {
            try {
                log("Url",url)
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (e: Exception) {
                toast(activity.getString(R.string.browser_not_available), activity)
            }
        }

        fun playVideo(url: String, activity: Activity)
        {
            log("Url",url)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setDataAndType(Uri.parse(url), "video/mp4")
            activity.startActivity(intent)
        }

        fun switchActivity(intent: Intent, activity: Activity) {
            activity.startActivity(intent)
            animateActivity(activity)
        }

        fun switchActivity(intent: Intent, requestCode: Int, activity: Activity) {
            activity.startActivityForResult(intent, requestCode)
            animateActivity(activity)
        }

        fun switchActivity(intent: Intent, requestCode: Int, activity: Activity, fragment: Fragment) {
            fragment.startActivityForResult(intent, requestCode)
            animateActivity(activity)
        }

        fun switchActivityWithClearFlag(intent: Intent, activity: Activity) {
            activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            animateActivity(activity)
        }

        fun animateActivity(activity: Activity) {
            activity.overridePendingTransition(
                R.anim.fade_in_activity_switching,
                R.anim.fade_out_activity_switching
            )
        }

        /*Method is use to set User Data in share preference*/
        fun setUserData(userModel: UserBaseModel) {
            if(StringUtility.validateString(userModel.token)) {
                setToken(userModel.token!!)
            }
            putStringDataInPreferences(Constants.PREFERENCE_KEY_USER_DATA, Gson().toJson(userModel, UserBaseModel::class.java))
        }

        /*Method is use to set User Data in share preference*/
        fun setUserData(userModel: UserModel) {
            var userBaseModel = UserBaseModel()
            userBaseModel.user = userModel
            setUserData(userBaseModel)
        }

        /*Method is use to get User Data from share preference*/
        @JvmStatic
        fun getUserData(context: Context): UserModel {
            var userDataString: String = PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.PREFERENCE_KEY_USER_DATA, "")!!
            val userBaseModel = stringToObject(userDataString, UserBaseModel::class.java)
            return userBaseModel.user!!;
        }

        @JvmStatic
        fun getUserType(context: Context): String {
            var userDataString: String = PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.PREFERENCE_KEY_USER_DATA, "")!!
            val user = stringToObject(userDataString, UserBaseModel::class.java)
            return user.user!!.userType!!
        }

        /*Method to convert Json string to Object class*/
        fun <T> stringToObject(json: String?, clazz: Class<T>?): T {
            return Gson().fromJson(json, clazz)
        }

        fun setToken(token: String) {
            if (StringUtility.validateString(token)) {
                putStringDataInPreferences(Constants.PREFERENCE_KEY_USER_TOKEN, token)
            }
        }

        fun checkEmptyView(size: Int, textView: TextView) {
            if (size == 0) {
                textView.visibility = View.VISIBLE
            } else {
                textView.visibility = View.INVISIBLE
            }
        }

        fun checkEmptyView(size: Int, textView: TextView, view: LinearLayout) {
            if (size == 0) {
                textView.visibility = View.VISIBLE
                view.visibility = View.VISIBLE
            } else {
                textView.visibility = View.INVISIBLE
                view.visibility = View.INVISIBLE
            }
        }

        fun getLanguage(): String
        {
            return Constants.LANGUAGE_EN
        }

        fun setLoginStatus(status: Boolean) {
            putBooleanDataInPreferences(Constants.PREFERENCE_KEY_IS_LOGGED_IN, status)
        }

        @JvmStatic
        fun tint(view: ImageView, colorCode: Int, context: Context) {
            view.setColorFilter(ContextCompat.getColor(context, colorCode), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        @JvmStatic
        open fun replaceFragment(layout: Int, fragment: Fragment, bundle: Bundle?, isAddToBackStack: Boolean, supportFragmentManager: FragmentManager) {
            try {
                if (bundle != null) {
                    fragment.setArguments(bundle)
                }
                val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                transaction.replace(layout, fragment)
                transaction.replace(layout, fragment, fragment::class.java.getSimpleName())
                if (isAddToBackStack) {
                    transaction.addToBackStack(null)
                }
                transaction.commit()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        fun getTempList(): ArrayList<TempModel> {
            var list: ArrayList<TempModel> = ArrayList()
            for (i in 0 until 300) {
                var obj: TempModel = TempModel()
                obj.name = "Name  " + i
                obj.id = i
                list.add(obj)
            }
            list.get(0).selected = true
            return list
        }

        fun getTempList(size: Int): ArrayList<TempModel> {
            var list: ArrayList<TempModel> = ArrayList()
            for (i in 0 until size) {
                var obj: TempModel = TempModel()
                obj.name = "Name  " + i
                obj.id = i
                list.add(obj)
            }
            list.get(0).selected = true
            return list
        }

        /*This method will retur Horizontal layout manager*/
        fun getHorizontalLayoutManager(context: Context): LinearLayoutManager {
            return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        fun validateNextPageAvailableOrNot(listSize: Int) : Boolean
        {
            if(listSize == 0 || listSize < Constants.PAGE_LIMIT)
            {
                return false
            }else
            {
                return true
            }
        }

        fun getSplitedImageUrl(value : String) : String
        {
            var result = value
            val list: List<String> = value.split("/")
            if(list.size > 1) {
                result = list.get(list.size - 2) + "/" + list.get(list.size - 1)
            }
            log("Splitted Url","Splitted Url = "+result)
            return result
        }

        fun onForceLogout(context: Context, message: String, activity: Activity) {
            if(StringUtility.validateString(message)) {
                Handler(Looper.getMainLooper()).post {
                    toastLong(message, context)
                }
            }
            //clearPreferences()
            setLoginStatus(false)
            putStringDataInPreferences(Constants.PREFERENCE_KEY_USER_DATA, "")
            putStringDataInPreferences(Constants.PREFERENCE_KEY_USER_TOKEN, "")
            removeAllNotifications(context)
            switchActivityWithClearFlag(Intent(context, MainActivity::class.java), activity)
            stopSocketService(context)
        }

        fun removeAllNotifications(context: Context) {
            val nMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nMgr.cancelAll()
        }

        fun stopSocketService(context: Context) {

        }

        fun isPermissionsGranted(permissions: Array<out String>, context: Context) : Boolean{
            var isAllPermissionGranted = true
            for((index, item) in permissions.withIndex())
            {
                if(ActivityCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED)
                {
                    isAllPermissionGranted = false
                    break
                }
            }
            return isAllPermissionGranted
        }

        fun needToShowRationalPermissionDialog(permissions: Array<out String>, activity: Activity) : Boolean{
            var needToShowRationalPermissionDialog = false
            for((index, item) in permissions.withIndex())
            {
                /*if(activity.shouldShowRequestPermissionRationale(item))
                {
                    needToShowRationalPermissionDialog = true
                    break
                }*/
            }
            return needToShowRationalPermissionDialog
        }

        @JvmStatic
        fun getVideoThumbnail(videoPath: String): Bitmap {
            log("File url", "file path for thumbnail = " + videoPath)
            val thumbnail = ThumbnailUtils.createVideoThumbnail(File(videoPath).path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND)
            return thumbnail!!
        }

        fun getLocalisedText(textEnglish: String?, textArabic: String?) : String
        {
            var result = ""
            if(getLanguage().equals(Constants.LANGUAGE_AR)) {
                if(textArabic != null) {
                    result = textArabic
                }
            }else
            {
                if(textEnglish != null) {
                    result = textEnglish
                }
            }
            return result
        }

        fun getSlotsList() : ArrayList<MyExtraData>
        {
            var slotsList: ArrayList<MyExtraData> = ArrayList()

            var obj1 = MyExtraData()
            obj1.time = "0:00"
            obj1.hour = 0
            obj1.minute = 0

            var obj2 = MyExtraData()
            obj2.time = "0:30"
            obj2.hour = 0
            obj2.minute = 30

            var obj3 = MyExtraData()
            obj3.time = "1:00"
            obj3.hour = 1
            obj3.minute = 0

            var obj4 = MyExtraData()
            obj4.time = "1:30"
            obj4.hour = 1
            obj4.minute = 30

            var obj5 = MyExtraData()
            obj5.time = "2:00"
            obj5.hour = 2
            obj5.minute = 0

            var obj6 = MyExtraData()
            obj6.time = "2:30"
            obj6.hour = 2
            obj6.minute = 30

            var obj7 = MyExtraData()
            obj7.time = "3:00"
            obj7.hour = 3
            obj7.minute = 0

            var obj8 = MyExtraData()
            obj8.time = "3:30"
            obj8.hour = 3
            obj8.minute = 30

            var obj9 = MyExtraData()
            obj9.time = "4:00"
            obj9.hour = 4
            obj9.minute = 0

            var obj10 = MyExtraData()
            obj10.time = "4:30"
            obj10.hour = 4
            obj10.minute = 30

            var obj11 = MyExtraData()
            obj11.time = "5:00"
            obj11.hour = 5
            obj11.minute = 0

            var obj12 = MyExtraData()
            obj12.time = "5:30"
            obj12.hour = 5
            obj12.minute = 30

            var obj13 = MyExtraData()
            obj13.time = "6:00"
            obj13.hour = 6
            obj13.minute = 0

            var obj14 = MyExtraData()
            obj14.time = "6:30"
            obj14.hour = 6
            obj14.minute = 30

            var obj15 = MyExtraData()
            obj15.time = "7:00"
            obj15.hour = 7
            obj15.minute = 0

            var obj16 = MyExtraData()
            obj16.time = "7:30"
            obj16.hour = 7
            obj16.minute = 30

            var obj17 = MyExtraData()
            obj17.time = "8:00"
            obj17.hour = 8
            obj17.minute = 0

            var obj18 = MyExtraData()
            obj18.time = "8:30"
            obj18.hour = 8
            obj18.minute = 30

            var obj19 = MyExtraData()
            obj19.time = "9:00"
            obj19.hour = 9
            obj19.minute = 0

            var obj20 = MyExtraData()
            obj20.time = "9:30"
            obj20.hour = 9
            obj20.minute = 30

            var obj21 = MyExtraData()
            obj21.time = "10:00"
            obj21.hour = 10
            obj21.minute = 0

            var obj22 = MyExtraData()
            obj22.time = "10:30"
            obj22.hour = 10
            obj22.minute = 30

            var obj23 = MyExtraData()
            obj23.time = "11:00"
            obj23.hour = 11
            obj23.minute = 0

            var obj24 = MyExtraData()
            obj24.time = "11:30"
            obj24.hour = 11
            obj24.minute = 30

            slotsList.add(obj1)
            slotsList.add(obj2)
            slotsList.add(obj3)
            slotsList.add(obj4)
            slotsList.add(obj5)
            slotsList.add(obj6)
            slotsList.add(obj7)
            slotsList.add(obj8)
            slotsList.add(obj9)
            slotsList.add(obj10)
            slotsList.add(obj11)
            slotsList.add(obj12)
            slotsList.add(obj13)
            slotsList.add(obj14)
            slotsList.add(obj15)
            slotsList.add(obj16)
            slotsList.add(obj17)
            slotsList.add(obj18)
            slotsList.add(obj19)
            slotsList.add(obj20)
            slotsList.add(obj21)
            slotsList.add(obj22)
            slotsList.add(obj23)
            slotsList.add(obj24)

            return slotsList
        }

        fun generateNotification(
            title: String?,
            messageBody: String?,
            intent: Intent,
            context: Context
        ) {
            val pendingIntent = PendingIntent.getActivity(
                context,
                System.currentTimeMillis().toInt(),
                intent,
                PendingIntent.FLAG_MUTABLE
            )
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val channelId = context.getString(R.string.default_notification_channel_id)
            val notificationBuilder =
                NotificationCompat.Builder(context, channelId)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_push_notification)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(0L))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    NotificationChannel.DEFAULT_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(
                System.currentTimeMillis().toInt(),
                notificationBuilder.build()
            )
        }

        fun isActivityInForeground(activity: Activity) : Boolean{
            var isActivityInForeGround = false
            val am = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = am.getRunningTasks(1)
            if (list != null && list.size > 0) {
                if(activity.componentName.shortClassName.equals(list.get(0).topActivity!!.shortClassName))
                {
                    isActivityInForeGround = true
                }
            }
            log("====isActivityInForeground====", "======================= "+isActivityInForeGround)
            return isActivityInForeGround
        }

        fun needToRemove()
        {

        }

        fun req_height(neededHeight: Int, activity: Context?): Int {
            val displaymetrics = DisplayMetrics()
            scanForActivity(activity)!!.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics)
            val height: Int = displaymetrics.heightPixels + statusBarHeight(activity!!)
            return height * neededHeight / 100
        }

        fun req_width(neededWidth: Int, activity: Context?): Int {
            val displaymetrics = DisplayMetrics()
            scanForActivity(activity)!!.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics)
            val width = displaymetrics.widthPixels
            return width * neededWidth / 100
        }

        private fun scanForActivity(cont: Context?): Activity? {
            if (cont == null) return null else if (cont is Activity) return cont else if (cont is ContextWrapper) return scanForActivity(
                cont.baseContext
            )
            return null
        }

        fun statusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        @JvmStatic
        fun isArabic() : Boolean
        {
            var isArabic = false
            var language = getLanguage()
            if(language.equals(BaseApplication.mContext.getString(R.string.arabic_language_code)))
            {
                isArabic = true
            }
            return isArabic
        }
    }
}