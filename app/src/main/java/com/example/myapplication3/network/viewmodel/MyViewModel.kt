package com.example.myapplication3.network.viewmodel

import android.app.Application
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.example.myapplication3.BaseApplication
import com.example.myapplication3.BuildConfig
import com.example.myapplication3.data.models.UserBaseModel
import com.example.myapplication3.models.*
import com.example.myapplication3.network.requestbuilder.RequestBuilder
import com.example.myapplication3.network.response.JsonArrayResponse
import com.example.myapplication3.network.response.JsonObjectResponse
import com.example.myapplication3.network.response.Resource
import com.example.myapplication3.network.response.ResourceFail
import com.example.myapplication3.utils.Constants
import com.example.myapplication3.utils.StringUtility
import com.example.myapplication3.utils.Utils
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyViewModel(application: Application) : BaseViewModel(application) {

    private fun getHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        /*headers.put("accept-language", "en")
        headers.put("x-example.myapplication3-platform", "android")
        headers.put("x-example.myapplication3-version", BuildConfig.VERSION_NAME)

        if(StringUtility.validateString(Utils.getToken()))
        {
            headers.put("Authorization", Utils.getToken()!!)
        }*/

        return headers
    }

    var loginObserver = MutableLiveData<Resource<Response<JsonObjectResponse<UserBaseModel>>>>()
    var signUpObserver = MutableLiveData<Resource<Response<JsonObjectResponse<UserBaseModel>>>>()
    var resetPasswordObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var languageObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var requestOtpObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var verifyOtpObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var checkSocialObserver = MutableLiveData<Resource<Response<JsonObjectResponse<UserBaseModel>>>>()
    var logOutObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var countriesObserver = MutableLiveData<Resource<Response<JsonArrayResponse<CommonDataModel>>>>()
    var citiesObserver = MutableLiveData<Resource<Response<JsonArrayResponse<CommonDataModel>>>>()
    var caseTypesObserver = MutableLiveData<Resource<Response<JsonArrayResponse<CommonDataModel>>>>()
    var uploadFileObserver = MutableLiveData<Resource<Response<JsonArrayResponse<UploadFileModel>>>>()
    var failObserver = MutableLiveData<ResourceFail>()
    var uploadFileOnAmazonObserver = MutableLiveData<Resource<Response<Void>>>()
    var lawyerProfileBuildUpObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var createavailabilitySlotObserver = MutableLiveData<Resource<Response<JsonArrayResponse<BaseModel>>>>()
    var lawyersListObserver = MutableLiveData<Resource<Response<JsonArrayResponse<UserModel>>>>()
    var favouriteToggleObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var likeToggleObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var getLawyerProfileObserver = MutableLiveData<Resource<Response<JsonObjectResponse<UserModel>>>>()
    var lawyersHomeDataObserver = MutableLiveData<Resource<Response<JsonObjectResponse<LawyerHomeDataModel>>>>()
    var updateLawyerProfileObserver = MutableLiveData<Resource<Response<JsonObjectResponse<UserBaseModel>>>>()
    var getSlotsObserver = MutableLiveData<Resource<Response<JsonArrayResponse<SlotModel>>>>()
    var createBookingObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var bookingListObserver = MutableLiveData<Resource<Response<JsonArrayResponse<BookingModel>>>>()
    var deleteAvailabilitySlotObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var getAvailabilityDaysObserver = MutableLiveData<Resource<Response<JsonArrayResponse<Availability>>>>()
    var editAvailabilitySlotObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var deleteAccountObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var notificationsListObserver = MutableLiveData<Resource<Response<JsonArrayResponse<BaseModel>>>>()
    var rejectVideoCallObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var getWalletObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var cancelBookingObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BaseModel>>>>()
    var rescheduleBookingObserver = MutableLiveData<Resource<Response<JsonObjectResponse<BookingModel>>>>()
    var employeeListObserver = MutableLiveData<Resource<Response<JsonArrayResponse<UserModel>>>>()

    fun login(password : String, deviceToken : String, phone : String, countryCode : String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.password = password
            builder.deviceToken = deviceToken
            builder.phone = phone
            builder.countryCode = countryCode

            BaseApplication.instance.apiService.login(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<UserBaseModel>> {
                    override fun onResponse(
                        call: Call<JsonObjectResponse<UserBaseModel>>,
                        response: Response<JsonObjectResponse<UserBaseModel>>
                    ) {

                        loading.value = false
                        var message = getMessage(response.body()?.message, response.errorBody())
                        if (isForceUpdateNeeded(response.headers(), message) == false) {
                            if(isAllOk(response.code(), message)) {
                             loginObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                            }
                        }

                    }

                    override fun onFailure(
                        call: Call<JsonObjectResponse<UserBaseModel>>,
                        t: Throwable
                    ) {
                        onFail(t.toString())
                    }
                })
        }
    }

    fun signUp(socialType : String, socialId: String,  userType : String, userName : String, countryCode : String, phone : String, password : String, deviceToken : String, parentView : LinearLayout) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true
            parentView.visibility = View.INVISIBLE

            var builder = RequestBuilder()
            builder.socialType = socialType
            builder.socialId = socialId
            builder.userType = userType
            builder.fullName = userName
            builder.countryCode = countryCode
            builder.phone = phone
            builder.password = password
            builder.deviceToken = deviceToken

            BaseApplication.instance.apiService.signUp(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<UserBaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<UserBaseModel>>,
                    response: Response<JsonObjectResponse<UserBaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                         signUpObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<UserBaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                    parentView.visibility = View.VISIBLE
                }
            })
        }
    }

    fun resetPassword(countryCode : String, phone : String, password : String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.countryCode = countryCode
            builder.phone = phone
            builder.password = password

            BaseApplication.instance.apiService.resetPassword(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                          resetPasswordObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                         }
                    }

                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun updateLanguage(lang : String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.lang = lang

            BaseApplication.instance.apiService.updateLanguage(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            languageObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }

                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun requestOtp(type : String, countryCode : String, phone : String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.type = type
            builder.countryCode = countryCode
            builder.phone = phone

            BaseApplication.instance.apiService.requestOtp(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            requestOtpObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }

                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                    failObserver.value = ResourceFail(MyExtraData())
                }
            })
        }
    }

    fun verifyOtp(type : String, countryCode : String, phone : String, otp : String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.type = type
            builder.countryCode = countryCode
            builder.phone = phone
            builder.token = otp

            BaseApplication.instance.apiService.verifyOtp(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if (isAllOk(response.code(), message)) {
                            verifyOtpObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }

                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun checkSocial(socialType : String, socialId : String, deviceToken : String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.socialType = socialType
            builder.socialId = socialId
            builder.deviceToken = deviceToken

            BaseApplication.instance.apiService.checkSocial(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<UserBaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<UserBaseModel>>,
                    response: Response<JsonObjectResponse<UserBaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            checkSocialObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }

                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<UserBaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun logOut() {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            BaseApplication.instance.apiService.logOut(getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            logOutObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }

                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    failObserver.value = ResourceFail(MyExtraData())
                }
            })
        }
    }

    fun countries(search : String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            val requestHashMap = java.util.HashMap<String, String>()
            requestHashMap[RequestBuilder.mSearchValue] = search

            BaseApplication.instance.apiService.countries(requestHashMap, getHeaders()).enqueue(object : Callback<JsonArrayResponse<CommonDataModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<CommonDataModel>>,
                    response: Response<JsonArrayResponse<CommonDataModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            countriesObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<CommonDataModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun cities(search : String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            val requestHashMap = java.util.HashMap<String, String>()
            requestHashMap[RequestBuilder.mSearchValue] = search

            BaseApplication.instance.apiService.cities(requestHashMap, getHeaders()).enqueue(object : Callback<JsonArrayResponse<CommonDataModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<CommonDataModel>>,
                    response: Response<JsonArrayResponse<CommonDataModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            citiesObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<CommonDataModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun caseTypes(loader: Boolean) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            if(loader) {
                loading.value = true
            }

            BaseApplication.instance.apiService.caseTypes(getHeaders()).enqueue(object : Callback<JsonArrayResponse<CommonDataModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<CommonDataModel>>,
                    response: Response<JsonArrayResponse<CommonDataModel>>
                ) {
                    if(loader) {
                        loading.value = false
                    }
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            caseTypesObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<CommonDataModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun uploadFile(location : String, type: String, count: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            val dataMap = java.util.HashMap<String, Any>()
            dataMap.put(RequestBuilder.mLocation, location)
            dataMap.put(RequestBuilder.mType, type)
            dataMap.put(RequestBuilder.mCount, count)

            BaseApplication.instance.apiService.uploadFile(dataMap, getHeaders()).enqueue(object : Callback<JsonArrayResponse<UploadFileModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<UploadFileModel>>,
                    response: Response<JsonArrayResponse<UploadFileModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            uploadFileObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<UploadFileModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                    failObserver.value = ResourceFail(MyExtraData())
                }
            })
        }
    }

    fun uploadFileOnAmazonApi(url: String, file: RequestBody, loader: Boolean) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            if(loader) {
                loading.value = true
            }
            BaseApplication.instance.apiService.uploadFileOnAmazon(url, file).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(loader) {
                        loading.value = false
                    }
                    uploadFileOnAmazonObserver.value = Resource(response.code(), response, "", MyExtraData())
                }

                override fun onFailure(
                    call: Call<Void>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun lawyerProfileBuildUp(userModel: UserModel) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.description = userModel.description
            builder.experience_in_years = userModel.experienceInYears
            builder.caseTypes = userModel.caseTypes
            builder.othercCaseTypes = userModel.othercCaseTypes
            builder.licenceImg = userModel.licenceImg
            builder.licenceExpiryDate = userModel.licenceExpiryDate.toString()
            builder.introVideo = userModel.introVideo
            builder.userId = userModel.id
            builder.city = userModel.cityId
            builder.consultationFee = userModel.consultationFee

            BaseApplication.instance.apiService.lawyerProfileBuildup(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            lawyerProfileBuildUpObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }

                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun createAvailabilitySlot(lawyerId: String, startDate: String, endDate: String, workingDayNames: ArrayList<String>) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.lawyerId = lawyerId
            builder.startDate = startDate
            builder.endDate = endDate
            builder.workingDayNames = workingDayNames

            BaseApplication.instance.apiService.createAvailabilitySlot(builder, getHeaders()).enqueue(object : Callback<JsonArrayResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<BaseModel>>,
                    response: Response<JsonArrayResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            createavailabilitySlotObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun lawyersList(page: Int, searchValue: String, keepKeyBoard: Boolean, categoryList: ArrayList<String>) {
        if (Utils.isNetworkAvailable(getApplication())) {
            if(!keepKeyBoard) {
                hideKeyBoard.value = ""
            }
            loading.value = true

            var builder = RequestBuilder()
            builder.page = page
            builder.perPage = Constants.PAGE_LIMIT
            if(StringUtility.validateString(searchValue)) {
                builder.searchValue = searchValue
            }
            if(categoryList.size > 0)
            {
                builder.filterByCaseType = categoryList
            }

            BaseApplication.instance.apiService.lawyersList(builder, getHeaders()).enqueue(object : Callback<JsonArrayResponse<UserModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<UserModel>>,
                    response: Response<JsonArrayResponse<UserModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            lawyersListObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<UserModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun favouriteToggle(userId: String, lawyerId: String, isFavourite: Boolean) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.userId = userId
            builder.lawyerId = lawyerId
            builder.isFavourite = isFavourite

            BaseApplication.instance.apiService.favouriteToggle(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            favouriteToggleObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun likeToggle(userId: String, lawyerId: String, isFavourite: Boolean) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.userId = userId
            builder.lawyerId = lawyerId
            builder.isFavourite = isFavourite

            BaseApplication.instance.apiService.likeToggle(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            likeToggleObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun getLawyerProfile(id: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            val requestHashMap = java.util.HashMap<String, String>()
            requestHashMap[RequestBuilder.mId] = id

            BaseApplication.instance.apiService.profile(requestHashMap, getHeaders()).enqueue(object : Callback<JsonObjectResponse<UserModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<UserModel>>,
                    response: Response<JsonObjectResponse<UserModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            getLawyerProfileObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<UserModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun lawyersHomeData(page: Int) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            val requestHashMap = java.util.HashMap<String, Any>()
            requestHashMap.put(RequestBuilder.mPage, page)
            requestHashMap.put(RequestBuilder.mPerPage, Constants.PAGE_LIMIT)

            BaseApplication.instance.apiService.lawyersHomeData(requestHashMap, getHeaders()).enqueue(object : Callback<JsonObjectResponse<LawyerHomeDataModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<LawyerHomeDataModel>>,
                    response: Response<JsonObjectResponse<LawyerHomeDataModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            lawyersHomeDataObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<LawyerHomeDataModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun updateLawyerProfile(builder : RequestBuilder) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            BaseApplication.instance.apiService.updateLawyerProfile(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<UserBaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<UserBaseModel>>,
                    response: Response<JsonObjectResponse<UserBaseModel>>
                ) {

                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            updateLawyerProfileObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<UserBaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun getSlots(lawyerId: String, date: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.lawyerId = lawyerId
            builder.date = date

            BaseApplication.instance.apiService.getSlots(builder, getHeaders()).enqueue(object : Callback<JsonArrayResponse<SlotModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<SlotModel>>,
                    response: Response<JsonArrayResponse<SlotModel>>
                ) {
                    loading.value = false

                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            getSlotsObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<SlotModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun createBooking(userId: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.userId = userId
            builder.lawyerId =  Constants.CREATE_BOOKING_LAWYER_ID
            builder.availabilitySlotId = Constants.CREATE_BOOKING_AVAILABILITY_SLOT_ID
            builder.slotId = Constants.CREATE_BOOKING_SLOTS_LIST
            builder.start = Constants.CREATE_BOOKING_START_TIME
            builder.end = Constants.CREATE_BOOKING_END_TIME
            builder.description = Constants.CREATE_BOOKING_SHORT_SUMMARY
            if(StringUtility.validateString(Constants.CREATE_BOOKING_DOCUMENT)) {
                builder.document = Constants.CREATE_BOOKING_DOCUMENT
            }
            builder.caseType = Constants.CREATE_BOOKING_CATEGORY
            builder.consultationType = Constants.CREATE_BOOKING_CONSULTATION_TYPE
            builder.communicationMethod = Constants.CREATE_BOOKING_COMMUNICATION_METHOD


            BaseApplication.instance.apiService.createBooking(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            createBookingObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun bookingList(page: Int, type: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            val requestHashMap = java.util.HashMap<String, Any>()
            requestHashMap.put(RequestBuilder.mPage, page)
            requestHashMap.put(RequestBuilder.mPerPage, Constants.PAGE_LIMIT)
            requestHashMap.put(RequestBuilder.mType, type)

            BaseApplication.instance.apiService.bookingList(requestHashMap, getHeaders()).enqueue(object : Callback<JsonArrayResponse<BookingModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<BookingModel>>,
                    response: Response<JsonArrayResponse<BookingModel>>
                ) {
                    loading.value = false

                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            bookingListObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<BookingModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun deleteAvailabilitySlot(lawyerId: String, availabilitySlotId: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.lawyerId = lawyerId
            builder.availabilitySlotId = availabilitySlotId

            BaseApplication.instance.apiService.deleteAvailabilitySlot(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            deleteAvailabilitySlotObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun getAvailabilityDays() {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            BaseApplication.instance.apiService.getAvailabilityDays(getHeaders()).enqueue(object : Callback<JsonArrayResponse<Availability>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<Availability>>,
                    response: Response<JsonArrayResponse<Availability>>
                ) {
                    loading.value = false

                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            getAvailabilityDaysObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<Availability>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun editAvailabilitySlot(lawyerId: String, availabilitySlotId: String, startDate: String, endDate: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.lawyerId = lawyerId
            builder.availabilitySlotId = availabilitySlotId
            builder.startDate = startDate
            builder.endDate = endDate

            BaseApplication.instance.apiService.editAvailabilitySlot(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            editAvailabilitySlotObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun deleteAccount(userId: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            BaseApplication.instance.apiService.deleteAccount(userId, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            deleteAccountObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun notificationsList(page: Int) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            val requestHashMap = java.util.HashMap<String, Any>()
            requestHashMap.put(RequestBuilder.mPage, page)
            requestHashMap.put(RequestBuilder.mPerPage, Constants.PAGE_LIMIT)

            BaseApplication.instance.apiService.notificationsList(requestHashMap, getHeaders()).enqueue(object : Callback<JsonArrayResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<BaseModel>>,
                    response: Response<JsonArrayResponse<BaseModel>>
                ) {
                    loading.value = false

                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            notificationsListObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun rejectVideoCall(appointmentId: String, roomId: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            BaseApplication.instance.apiService.rejectVideoCall(appointmentId, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            rejectVideoCallObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun getWallet() {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            BaseApplication.instance.apiService.getWallet(getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            getWalletObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun cancelBooking(id: String, cancelReason: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.id = id
            if(StringUtility.validateString(cancelReason)) {
                builder.cancelReason = cancelReason
            }

            BaseApplication.instance.apiService.cancelBooking(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BaseModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    response: Response<JsonObjectResponse<BaseModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            cancelBookingObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BaseModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun rescheduleBooking(id: String) {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            var builder = RequestBuilder()
            builder.bookingId = id
            builder.availabilitySlotId = Constants.CREATE_BOOKING_AVAILABILITY_SLOT_ID
            builder.slotId = Constants.CREATE_BOOKING_SLOTS_LIST
            builder.start = Constants.CREATE_BOOKING_START_TIME
            builder.end = Constants.CREATE_BOOKING_END_TIME

            BaseApplication.instance.apiService.rescheduleBooking(builder, getHeaders()).enqueue(object : Callback<JsonObjectResponse<BookingModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BookingModel>>,
                    response: Response<JsonObjectResponse<BookingModel>>
                ) {
                    loading.value = false
                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            rescheduleBookingObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<BookingModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }

    fun employeeList() {
        if (Utils.isNetworkAvailable(getApplication())) {
            hideKeyBoard.value = ""
            loading.value = true

            BaseApplication.instance.apiService.employeeList().enqueue(object : Callback<JsonArrayResponse<UserModel>> {
                override fun onResponse(
                    call: Call<JsonArrayResponse<UserModel>>,
                    response: Response<JsonArrayResponse<UserModel>>
                ) {
                    loading.value = false

                    var message = getMessage(response.body()?.message, response.errorBody())
                    if (isForceUpdateNeeded(response.headers(), message) == false) {
                        if(isAllOk(response.code(), message)) {
                            employeeListObserver.value = Resource(response.code(), response, "" + message, MyExtraData())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<JsonArrayResponse<UserModel>>,
                    t: Throwable
                ) {
                    onFail(t.toString())
                }
            })
        }
    }
}