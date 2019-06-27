package in.ac.bits_hyderabad.swd.swd.helper;

public class Person {

    public String name;
    public String designation;
    public String phone;
    public String uid;
    public String heading;
    public String subheading;

    public Person (String name,String designation, String phone, String uid, String heading, String subheading)
    {
        this.name=name;
        this.designation=designation;
        this.phone=phone;
        this.uid=uid;
        this.heading=heading;
        this.subheading=subheading;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }
}
