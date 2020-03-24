package `in`.ac.bits_hyderabad.swd.swd.databaseconnection

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
    @SerializedName("tag")
    var tag: String? = null
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
