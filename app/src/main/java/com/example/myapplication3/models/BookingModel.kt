package com.example.myapplication3.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BookingModel : BaseModel()
{
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("document")
    @Expose
    var document: String? = null

    @SerializedName("duration")
    @Expose
    var duration: Int? = null

    @SerializedName("consultationFee")
    @Expose
    var consultationFee: Int? = null

    @SerializedName("consultationType")
    @Expose
    var consultationType: String? = null

    @SerializedName("start")
    @Expose
    var start: Long? = null

    @SerializedName("bookingNumber")
    @Expose
    var bookingNumber: String? = null

    @SerializedName("reshedule")
    @Expose
    var reshedule: Boolean? = null

    @SerializedName("avatar")
    @Expose
    var avatar: String? = null

    @SerializedName("availabilityStatus")
    @Expose
    var availabilityStatus: String? = null

    @SerializedName("lawyerId")
    @Expose
    var lawyerId: String? = null

    override fun equals(obj: Any?): Boolean {
        return (obj as? BookingModel)?.id?.equals(id) ?: super.equals(obj)
    }

}