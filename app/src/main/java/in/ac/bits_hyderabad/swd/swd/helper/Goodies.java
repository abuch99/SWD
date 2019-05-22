package in.ac.bits_hyderabad.swd.swd.helper;

public class Goodies {
    public String id;
    public String name;
    public String host;
    public String image;
    public String price;
    public String host_name;
    public String mobile;

    public Goodies(String id, String name, String host, String price, String host_name, String mobile)
    {
        this.id=id;
        this.name=name;
        this.host=host;
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
