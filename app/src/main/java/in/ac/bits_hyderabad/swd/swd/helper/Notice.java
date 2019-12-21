package in.ac.bits_hyderabad.swd.swd.helper;

public class Notice {
    String pid;
    String uid;
    String uname;
    String category;
    String title;
    String subtitle;
    String body;
    String link;
    String image;
    String expiry;
    String utime;

    public Notice(String pid, String uid, String uname, String category, String title, String subtitle, String body, String link, String image, String expiry, String utime) {
        this.pid = pid;
        this.uid = uid;
        this.uname = uname;
        this.category = category;
        this.title = title;
        this.subtitle = subtitle;
        this.body = body;
        this.link = link;
        this.image = image;
        this.expiry = expiry;
        this.utime = utime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }
}
