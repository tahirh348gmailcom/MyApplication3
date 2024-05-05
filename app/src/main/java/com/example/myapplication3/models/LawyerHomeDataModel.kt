package com.example.myapplication3.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LawyerHomeDataModel : BaseModel()
{
    @SerializedName("recommendedLawyers")
    @Expose
    var recommendedLawyers: List<UserModel>? = null

    @SerializedName("onlineLawyers")
    @Expose
    var onlineLawyers: OnlineLawyers? = null

    @SerializedName("directClosedConsultation")
    @Expose
    var directClosedConsultation: Int? = null

    @SerializedName("reservedClosedConsultation")
    @Expose
    var reservedClosedConsultation: Int? = null

    @SerializedName("upcommingConsultations")
    @Expose
    var upcommingConsultations: Int? = null

    @SerializedName("onlineHours")
    @Expose
    var onlineHours: Double? = null

    @SerializedName("totalAmountEarned")
    @Expose
    var totalAmountEarned: Double? = null

    @SerializedName("remainingFees")
    @Expose
    var remainingFees: Double? = null

    class OnlineLawyers {
        @SerializedName("page")
        @Expose
        var page: Int? = null

        @SerializedName("perPage")
        @Expose
        var perPage: Int? = null

        @SerializedName("count")
        @Expose
        var count: Int? = null

        @SerializedName("records")
        @Expose
        var records: List<UserModel>? = null

        @SerializedName("hasMore")
        @Expose
        var hasMore: Boolean? = null
    }
}