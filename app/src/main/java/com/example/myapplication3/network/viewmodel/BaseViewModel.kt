package com.example.myapplication3.network.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication3.utils.Constants
import com.example.myapplication3.utils.StringUtility
import com.example.myapplication3.utils.Utils
import org.json.JSONObject

import okhttp3.Headers
import okhttp3.ResponseBody

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var error: MutableLiveData<String> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var forceUpdate: MutableLiveData<String> = MutableLiveData()
    var softUpdate: MutableLiveData<String> = MutableLiveData()
    var hideKeyBoard: MutableLiveData<String> = MutableLiveData()
    var forceLogout: MutableLiveData<String> = MutableLiveData()

    fun onMyResponse(
        responseCode: Int,
        message: String?,
        errorBody: ResponseBody?,
        header: Headers
      ): Boolean {

        var success: Boolean = false

        val errorMessage = getErrorMessage(errorBody)
        val finalMessage = getFinalMessage(message, errorMessage)

        val forceUpdateRequired = isForceUpdateNeeded(header, finalMessage)
        if (forceUpdateRequired != null && forceUpdateRequired == true) {
            return success
        }

        if (responseCode == Constants.SUCCESS) {
            success = true
        } else {
               error.setValue(""+finalMessage)

        }
        return success
    }



    fun getMessage(message: String?, errorBody: ResponseBody?): String?
    {
        val errorMessage = getErrorMessage(errorBody)
        val finalMessage = getFinalMessage(message, errorMessage)

        return finalMessage
    }

     fun isForceUpdateNeeded(header: Headers, finalMessage: String?): Boolean? {
        var needForceUpdate: Boolean? = false

        val updateAvailable = header.get("update_available")
        val forceUpdate = header.get("force_update")



        if (updateAvailable != null &&  StringUtility.validateString(updateAvailable) && updateAvailable.equals(
                "true",
                ignoreCase = true
            )
        ) {
            if (StringUtility.validateString(forceUpdate!!) && forceUpdate.equals(
                    "true",
                    ignoreCase = true
                )
            ) {
                //force update
                this.forceUpdate.value = finalMessage
                needForceUpdate = true
            } else {
                //soft update
                this.softUpdate.value = "New update available"
                needForceUpdate = false
            }

        }
        return needForceUpdate
    }

    private fun getErrorMessage(errorBody: ResponseBody?): String {
        var message = ""
        if (errorBody != null) {
            try {
                val jsonErrorObject = JSONObject(errorBody.string())
                message = jsonErrorObject.getString("message")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return message
    }

    private fun getFinalMessage(successMessage: String?, errorMessage: String): String? {
        var finalMessage: String? = null
        if (successMessage != null && StringUtility.validateString(successMessage)) {
            finalMessage = successMessage
        } else if (StringUtility.validateString(errorMessage)) {
            finalMessage = errorMessage
        }
        return finalMessage
    }

    fun onFail(exception: String) {
        loading.value = false
        error.value = exception
        Utils.log("Error----", "----"+exception)
    }

    fun isAllOk(code: Int, message: String?) : Boolean {
        var allGood = true
        if(code == Constants.CODE_401)
        {
            allGood = false
            this.forceLogout.value = message
        }
        return allGood
    }

}
