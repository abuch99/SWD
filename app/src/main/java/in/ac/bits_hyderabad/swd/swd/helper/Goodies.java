package in.ac.bits_hyderabad.swd.swd.helper;

public class Goodies {
    public String id;
    public String name;
    public String host;
    public String image;
    public String price;
    public String host_name;
    public String mobile;

    /*{"g_id":"1000",
    "name":"Duplicate ID Card",
    "hosted_by":"Student Welfare Division",
    "img":"bits_logo.png",
    "link":"",
    "active":"1",
    "xs":"0",
    "s":"0",
    "m":"0",
    "l":"0",
    "xl":"0",
    "xxl":"0",
    "xxxl":"0",
    "qut":"0",
    "min_amount":"0",
    "max_amount":"0",
    "max_quantity":"0",
    "price":"75",
    "closing_datetime":"2019-05-14 00:00:00",
    "delivery_date":"0000-00-00",
    "custom":"0",
    "acceptance":"0",
    "hoster_name":"Student Welfare Division",
    "hoster_mob_num":"",
    "view_uid":"prasanth",
    "uploaded_on":"2019-04-14 15:08:37"}
     */
    public Goodies(String id, String name, String host,String image, String price, String host_name, String mobile)
    {
        this.id=id;
        this.name=name;
        this.host=host;
        this.image=image;
        this.price=price;
        this.host_name=host_name;
        this.mobile=mobile;
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
}
