package com.example.myapplication3.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserModel : BaseModel(){

    @SerializedName("employee_name")
    @Expose
    var employeeName: String? = null

    @SerializedName("licenceImg")
    @Expose
    var licenceImg: String? = null
    @SerializedName("licenceExpiryDate")
    @Expose
    var licenceExpiryDate: Long? = null
    @SerializedName("introVideo")
    @Expose
    var introVideo: String? = null
    @SerializedName("caseTypes")
    @Expose
    var caseTypes: ArrayList<String>? = null
    @SerializedName("othercCaseTypes")
    @Expose
    var othercCaseTypes: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("experience_in_years")
    @Expose
    var experienceInYears: Int? = null
    @SerializedName("_id")
    @Expose
    var id: String? = null
    @SerializedName("cityId")
    @Expose
    var cityId: String? = null
    @SerializedName("city")
    @Expose
    var city: City? = null
    @SerializedName("isLawyerProfileComplete")
    @Expose
    var isLawyerProfileComplete: Boolean? = null
    @SerializedName("isLawyerApproved")
    @Expose
    var isLawyerApproved: Boolean? = null
    @SerializedName("userType")
    @Expose
    var userType: String? = null
    @SerializedName("consultationFee")
    @Expose
    var consultationFee: Int? = null

    @SerializedName("availabilitySlots")
    @Expose
    var availabilitySlots: ArrayList<Availability>? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("isFav")
    @Expose
    var isFav: Boolean? = null

    @SerializedName("isLike")
    @Expose
    var isLike: Boolean? = null

    @SerializedName("likeCount")
    @Expose
    var likeCount: Int? = null

    @SerializedName("cases")
    @Expose
    var cases: List<CommonDataModel>? = null

    @SerializedName("availabilityStatus")
    @Expose
    var availabilityStatus: String? = null

    @SerializedName("formattedPhone")
    @Expose
    var formattedPhone: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null
    @SerializedName("countryCode")
    @Expose
    var countryCode: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("pushNotificationAllowed")
    @Expose
    var pushNotificationAllowed: Boolean? = null

    class City {
        @SerializedName("_id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("name_ar")
        @Expose
        var nameAr: String? = null
    }
}