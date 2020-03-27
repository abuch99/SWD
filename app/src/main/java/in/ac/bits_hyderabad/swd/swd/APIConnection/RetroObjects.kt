package `in`.ac.bits_hyderabad.swd.swd.APIConnection

import com.google.gson.annotations.SerializedName

class MessMenu {
    @SerializedName("mess")
    var mess: String? = null
    @SerializedName("menu_id")
    var menuId: String? = null
    @SerializedName("Day")
    var day: String? = null
    @SerializedName("Breakfast")
    var breakfast: String? = null
    @SerializedName("Lunch")
    var lunch: String? = null
    @SerializedName("Tiffin")
    var snacks: String? = null
    @SerializedName("Dinner")
    var dinner: String? = null
}

class MessReq {
    @SerializedName("error")
    var error: Boolean = true
    @SerializedName("data")
    var data: MessData? = null
}

class MessData {
    @SerializedName("mess")
    var mess: String? = null
    @SerializedName("reg_open")
    var regOpen: Boolean = false
    @SerializedName("message")
    var message: String? = null
}

class Login {
    @SerializedName("error")
    var error: Boolean? = null
    @SerializedName("uid")
    var uid: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("id_no")
    var idNo: String? = null
    @SerializedName("profile_completed")
    var profileCompleted: Boolean? = false
}

class Goodie {
    @SerializedName("g_id")
    var goodieID: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("hosted_by")
    var hostedBy: String? = null
    @SerializedName("img")
    var imgLink: String? = null
    @SerializedName("link")
    var sizeChartLink: String? = null
    @SerializedName("active")
    var active: String? = null
    @SerializedName("xs")
    var xs: String? = null
    @SerializedName("s")
    var s: String? = null
    @SerializedName("m")
    var m: String? = null
    @SerializedName("l")
    var l: String? = null
    @SerializedName("xl")
    var xl: String? = null
    @SerializedName("xxl")
    var xxl: String? = null
    @SerializedName("xxxl")
    var xxxl: String? = null
    @SerializedName("qut")
    var qut: String? = null
    @SerializedName("min_amount")
    var minAmount: String? = null
    @SerializedName("max_amount")
    var maxAmount: String? = null
    @SerializedName("max_quantity")
    var maxQuantity: String? = null
    @SerializedName("price")
    var price: String? = null
    @SerializedName("closing_datetime")
    var closingDate: String? = null
    @SerializedName("delivery_date")
    var deliveryDate: String? = null
    @SerializedName("custom")
    var custom: String? = null
    @SerializedName("acceptance")
    var acceptance: String? = null
    @SerializedName("hoster_name")
    var hosterName: String? = null
    @SerializedName("hoster_mob_num")
    var hosterNumber: String? = null
    @SerializedName("view_uid")
    var viewUid: String? = null
    @SerializedName("uploaded_on")
    var uploadedOn: String? = null
}

class Deduction {
    @SerializedName("id")
    var goodieID: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("xs")
    var xs: String? = null
    @SerializedName("s")
    var s: String? = null
    @SerializedName("m")
    var m: String? = null
    @SerializedName("l")
    var l: String? = null
    @SerializedName("xl")
    var xl: String? = null
    @SerializedName("xxl")
    var xxl: String? = null
    @SerializedName("xxxl")
    var xxxl: String? = null
    @SerializedName("qut")
    var qut: String? = null
    @SerializedName("netqut")
    var netqut: String? = null
    @SerializedName("amount")
    var amount: String? = null
    @SerializedName("type")
    var type: String? = null
}
