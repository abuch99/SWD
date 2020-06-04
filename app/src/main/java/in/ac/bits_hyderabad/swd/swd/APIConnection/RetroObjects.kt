package `in`.ac.bits_hyderabad.swd.swd.APIConnection

class MessMenu {
    var mess: String? = null
    var menu_id: String? = null
    var Day: String? = null
    var Breakfast: String? = null
    var Lunch: String? = null
    var Tiffin: String? = null
    var Dinner: String? = null
}

class MessReq {
    var error: Boolean = true
    var data: MessData? = null
}

class MessData {
    var mess: String? = null
    var reg_open: Boolean = false
    var message: String? = null
}

class Login {
    var error: Boolean? = null
    var uid: String? = null
    var name: String? = null
    var id_no: String? = null
    var room: String? = null
    var phone: String? = null
    var gender: String? = null
    var dob: String? = null
    var aadhaar: String? = null
    var pan_card: String? = null
    var category: String? = null
    var email: String? = null
    var blood: String? = null
    var homeadd: String? = null
    var city: String? = null
    var state: String? = null
    var nation: String? = null
    var father: String? = null
    var fphone: String? = null
    var fmail: String? = null
    var foccup: String? = null
    var fcomp: String? = null
    var fdesg: String? = null
    var mother: String? = null
    var mmail: String? = null
    var hphone: String? = null
    var moccup: String? = null
    var mcomp: String? = null
    var mdesg: String? = null
    var med_history: String? = null
    var current_med: String? = null
    var bank: String? = null
    var acno: String? = null
    var ifsc: String? = null
    var profile_completed: Boolean? = false
}

class UpdateLoginResponse {
    var error: Boolean = true
}

class Goodie {
    var g_id: String? = null
    var name: String? = null
    var hosted_by: String? = null
    var img: String? = null
    var link: String? = null
    var active: String? = null
    var xs: String? = null
    var s: String? = null
    var m: String? = null
    var l: String? = null
    var xl: String? = null
    var xxl: String? = null
    var xxxl: String? = null
    var qut: String? = null
    var min_amount: String? = null
    var max_amount: String? = null
    var max_quantity: String? = null
    var price: String? = null
    var closing_datetime: String? = null
    var delivery_date: String? = null
    var custom: String? = null
    var acceptance: String? = null
    var hoster_name: String? = null
    var hoster_mob_num: String? = null
    var view_uid: String? = null
    var uploaded_on: String? = null
}

class Deduction {
    var id: String? = null
    var name: String? = null
    var xs: String? = null
    var s: String? = null
    var m: String? = null
    var l: String? = null
    var xl: String? = null
    var xxl: String? = null
    var xxxl: String? = null
    var qut: String? = null
    var netqut: String? = null
    var amount: String? = null
    var type: String? = null
}

class MessReg {
    var Pass: String? = null
    var Mess1left: String? = null
    var Mess2left: String? = null
}

class GoodieOrderPlacedResponse {
    var error: Boolean = true
    var msg: String? = null
}

class MessRegistrationResponse {
    var Mess: String? = null
    var Mess1left: String? = null
    var Mess2left: String? = null
    var Pass: String? = null
}

class ConnectData {
    var data: ArrayList<Person>? = null
}

class Person {
    var designation: String? = null
    var phone: String? = null
    var uid: String? = null
    var name: String? = null
    var heading: String? = null
    var subheading: String? = null
    var order: String? = null
    var updated_at: Long? = null
}

class DocContents {
    var tag: String? = null
    var error: Boolean = true
    var content: String? = null
}
