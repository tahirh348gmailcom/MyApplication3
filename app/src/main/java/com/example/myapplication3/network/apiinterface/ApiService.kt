package com.example.myapplication3.network.apiinterface


import com.example.myapplication3.data.models.UserBaseModel
import com.example.myapplication3.models.*
import com.example.myapplication3.network.requestbuilder.RequestBuilder
import com.example.myapplication3.network.response.JsonArrayResponse
import com.example.myapplication3.network.response.JsonObjectResponse
import com.example.myapplication3.twilio.models.VideoTokenResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("employees")
    fun employeeList(): Call<JsonArrayResponse<UserModel>>

    @POST("auth/log-in")
    fun login(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<UserBaseModel>>
    @POST("auth/sign-up")
    fun signUp(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<UserBaseModel>>
    @POST("auth/reset-password")
    fun resetPassword(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @POST("auth/update-language")
    fun updateLanguage(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @POST("auth/request-otp")
    fun requestOtp(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @POST("auth/verify-otp")
    fun verifyOtp(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @POST("auth/check-social")
    fun checkSocial(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<UserBaseModel>>
    @GET("auth/log-out")
    fun logOut(@HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @GET("users/countries")
    fun countries(@QueryMap queryMap: HashMap<String, String>, @HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<CommonDataModel>>
    @GET("users/cities")
    fun cities(@QueryMap queryMap: HashMap<String, String>, @HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<CommonDataModel>>
    @GET("users/caseTypes")
    fun caseTypes(@HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<CommonDataModel>>
    @GET("utils/upload-file")
    fun uploadFile(@QueryMap queryMap: HashMap<String, Any>, @HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<UploadFileModel>>
    @PUT
    fun uploadFileOnAmazon(@Url url: String, @Body image: RequestBody): Call<Void>
    @POST("lawyers/lawyer-profile-buildup")
    fun lawyerProfileBuildup(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @POST("lawyers/create-availability-slot")
    fun createAvailabilitySlot(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<BaseModel>>
    @POST("lawyers/list")
    fun lawyersList(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<UserModel>>
    @POST("lawyers/favourite-toggle")
    fun favouriteToggle(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @POST("lawyers/favourite-toggle")
    fun likeToggle(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @GET("lawyers/profile")
    fun profile(@QueryMap queryMap: HashMap<String, String>, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<UserModel>>
    @GET("lawyers/lawyers-home-data")
    fun lawyersHomeData(@QueryMap queryMap: HashMap<String, Any>, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<LawyerHomeDataModel>>
    @PUT("lawyers/profile")
    fun updateLawyerProfile(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<UserBaseModel>>
    @POST("bookings/get-slots")
    fun getSlots(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<SlotModel>>
    @POST("bookings/create-booking")
    fun createBooking(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @GET("bookings/booking-list")
    fun bookingList(@QueryMap queryMap: HashMap<String, Any>, @HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<BookingModel>>
    @POST("lawyers/delete-availability-slot")
    fun deleteAvailabilitySlot(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @GET("bookings/get-availability-days")
    fun getAvailabilityDays(@HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<Availability>>
    @POST("lawyers/edit-availability-slot")
    fun editAvailabilitySlot(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @GET("users/delete/{slug}")
    fun deleteAccount(@Path("slug") slug: String, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @GET("users/notifications")
    fun notificationsList(@QueryMap queryMap: HashMap<String, Any>, @HeaderMap headers: Map<String, String>): Call<JsonArrayResponse<BaseModel>>
    /*--------------------------For Video call-----------------------*/
    @GET("video/get-video-token/twilio/{appointmentId}")
    fun getVideoCallToken(@Path("appointmentId") appointmentId: String?): Call<JsonObjectResponse<VideoTokenResponse>>

    @GET("video/reject-call/twilio/{appointmentId}/{roomSid}")
    fun rejectVideoCall(@Path("appointmentId") appointmentId: String?, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    /*-----------------------End---------------------------*/
    @GET("wallet/getWallet")
    fun getWallet(@HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @POST("bookings/cancel-booking")
    fun cancelBooking(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BaseModel>>
    @POST("bookings/reschedule-booking")
    fun rescheduleBooking(@Body requestBuilder: RequestBuilder, @HeaderMap headers: Map<String, String>): Call<JsonObjectResponse<BookingModel>>
}