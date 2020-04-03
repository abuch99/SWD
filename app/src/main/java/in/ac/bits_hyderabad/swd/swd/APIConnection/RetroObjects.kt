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
    @SerializedName("room")
    var room: String? = null
    @SerializedName("phone")
    var phone: String? = null
    @SerializedName("gender")
    var gender: String? = null
    @SerializedName("dob")
    var dob: String? = null
    @SerializedName("aadhaar")
    var aadhaar: String? = null
    @SerializedName("pan_card")
    var pan_card: String? = null
    @SerializedName("category")
    var category: String? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("blood")
    var blood: String? = null
    @SerializedName("homeadd")
    var homeadd: String? = null
    @SerializedName("city")
    var city: String? = null
    @SerializedName("state")
    var state: String? = null
    @SerializedName("nation")
    var nation: String? = null
    @SerializedName("father")
    var father: String? = null
    @SerializedName("fphone")
    var fphone: String? = null
    @SerializedName("fmail")
    var fmail: String? = null
    @SerializedName("foccup")
    var foccup: String? = null
    @SerializedName("fcomp")
    var fcomp: String? = null
    @SerializedName("fdesg")
    var fdesg: String? = null
    @SerializedName("mother")
    var mother: String? = null
    @SerializedName("mmail")
    var mmail: String? = null
    @SerializedName("hphone")
    var hphone: String? = null
    @SerializedName("moccup")
    var moccup: String? = null
    @SerializedName("mcomp")
    var mcomp: String? = null
    @SerializedName("mdesg")
    var mdesg: String? = null
    @SerializedName("med_history")
    var med_history: String? = null
    @SerializedName("current_med")
    var current_med: String? = null
    @SerializedName("bank")
    var bank: String? = null
    @SerializedName("acno")
    var acno: String? = null
    @SerializedName("ifsc")
    var ifsc: String? = null
    @SerializedName("profile_completed")
    var profileCompleted: Boolean? = false
}

class UpdateLoginResponse {
    @SerializedName("error")
    var error: Boolean = true
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

class MessReg {
    @SerializedName("Pass")
    var pass: String? = null
    @SerializedName("Mess1left")
    var mess1SeatsLeft: String? = null
    @SerializedName("Mess2left")
    var mess2SeatsLeft: String? = null
}

class GoodieOrderPlacedResponse {
    @SerializedName("error")
    var error: Boolean = true
    @SerializedName("msg")
    var msg: String? = null
}

class MessRegistrationResponse {
    @SerializedName("Mess")
    var mess: String? = null
    @SerializedName("Mess1left")
    var mess1SeatsLeft: String? = null
    @SerializedName("Mess2left")
    var mess2SeatsLeft: String? = null
    @SerializedName("Pass")
    var pass: String? = null
}

class ConnectData {
    @SerializedName("data")
    var data: ArrayList<Person>? = null
}

class Person {
    @SerializedName("designation")
    var designation: String? = null
    @SerializedName("phone")
    var phone: String? = null
    @SerializedName("uid")
    var uid: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("heading")
    var heading: String? = null
    @SerializedName("subheading")
    var subheading: String? = null
    @SerializedName("order")
    var order: String? = null
    @SerializedName("updated_at")
    var updatedAt: Long? = null
}

class DocContents {
    @SerializedName("tag")
    var tag: String? = null
    @SerializedName("error")
    var error: Boolean = true
    @SerializedName("content")
    var content: String? = null
}
