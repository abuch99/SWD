package in.ac.bits_hyderabad.swd.swd.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class Goodies implements Parcelable {

    public String id;
    public String name;
    public String host;
    public String image;
    public String price;
    public String size_chart;
    public String xs;
    public String s;
    public String m;
    public String l;
    public String xl;
    public String xxl;
    public String xxxl;
    public String qut;
    public String min_amount;
    public String max_amount;
    public String max_quantity;
    public String custom;
    public String view_uid;
    public String host_name;
    public String mobile;

    /*{"g_id":"1000","name":"Duplicate ID Card","hosted_by":"Student Welfare Division","img":"bits_logo.png", "link":"","active":"1","xs":"0","s":"0","m":"0","l":"0","xl":"0","xxl":"0","xxxl":"0","qut":"0","min_amount":"0","max_amount":"0","max_quantity":"0","price":"75","closing_datetime":"2019-05-14 00:00:00","delivery_date":"0000-00-00","custom":"0","acceptance":"0","hoster_name":"Student Welfare Division","hoster_mob_num":"","view_uid":"prasanth","uploaded_on":"2019-04-14 15:08:37"} */

    public Goodies(String id, String name, String host, String image, String price,String size_chart, String xs, String s, String m, String l, String xl, String xxl, String xxxl, String qut, String min_amount, String max_amount, String max_quantity,  String custom, String view_uid,String host_name, String mobile ) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.image = image;
        this.price = price;
        this.size_chart = size_chart;
        this.xs = xs;
        this.s = s;
        this.m = m;
        this.l = l;
        this.xl = xl;
        this.xxl = xxl;
        this.xxxl= xxxl;
        this.qut = qut;
        this.min_amount = min_amount;
        this.max_amount = max_amount;
        this.max_quantity = max_quantity;
        this.custom = custom;
        this.view_uid = view_uid;
        this.host_name = host_name;
        this.mobile = mobile;

    }


    protected Goodies(Parcel in) {
        id = in.readString();
        name = in.readString();
        host = in.readString();
        image = in.readString();
        price = in.readString();
        size_chart = in.readString();
        xs = in.readString();
        s = in.readString();
        m = in.readString();
        l = in.readString();
        xl = in.readString();
        xxl = in.readString();
        xxxl = in.readString();
        qut = in.readString();
        min_amount = in.readString();
        max_amount = in.readString();
        max_quantity = in.readString();
        custom = in.readString();
        view_uid = in.readString();
        host_name = in.readString();
        mobile = in.readString();
    }

    public static final Creator<Goodies> CREATOR = new Creator<Goodies>() {
        @Override
        public Goodies createFromParcel(Parcel in) {
            return new Goodies(in);
        }

        @Override
        public Goodies[] newArray(int size) {
            return new Goodies[size];
        }
    };

    public String getSize_chart() {
        return size_chart;
    }

    public void setSize_chart(String size_chart) {
        this.size_chart = size_chart;
    }

    public String getXs() {
        return xs;
    }

    public void setXs(String xs) {
        this.xs = xs;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getXxl() {
        return xxl;
    }

    public void setXxl(String xxl) {
        this.xxl = xxl;
    }

    public String getXxxl() {
        return xxxl;
    }

    public void setXxxl(String xxxl) {
        this.xxxl = xxxl;
    }

    public String getQut() {
        return qut;
    }

    public void setQut(String qut) {
        this.qut = qut;
    }

    public String getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(String min_amount) {
        this.min_amount = min_amount;
    }

    public String getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(String max_amount) {
        this.max_amount = max_amount;
    }

    public String getMax_quantity() {
        return max_quantity;
    }

    public void setMax_quantity(String max_quantity) {
        this.max_quantity = max_quantity;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getView_uid() {
        return view_uid;
    }

    public void setView_uid(String view_uid) {
        this.view_uid = view_uid;
    }

    public static Creator<Goodies> getCREATOR() {
        return CREATOR;
    }

    public String getId() {
        return id;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(host);
        dest.writeString(image);
        dest.writeString(price);
        dest.writeString(size_chart);
        dest.writeString(xs);
        dest.writeString(s);
        dest.writeString(m);
        dest.writeString(l);
        dest.writeString(xl);
        dest.writeString(xxl);
        dest.writeString(xxxl);
        dest.writeString(qut);
        dest.writeString(min_amount);
        dest.writeString(max_amount);
        dest.writeString(max_quantity);
        dest.writeString(custom);
        dest.writeString(view_uid);
        dest.writeString(host_name);
        dest.writeString(mobile);
    }
}
