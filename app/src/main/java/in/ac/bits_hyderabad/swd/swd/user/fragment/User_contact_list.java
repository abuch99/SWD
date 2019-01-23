package in.ac.bits_hyderabad.swd.swd.user.fragment;

public class User_contact_list {

    private String name;
    private String number;
    private String desig;

    public User_contact_list(String name, String number, String desig)
    {
        this.name=name;
        this.number=number;
        this.desig=desig;
    }

    public String getname()
    {
        return name;
    }

    public void setname(String name)
    {
        this.name = name;
    }

    public String getnum()
    {
        return number;
    }

    public void setnum(String number)
    {
        this.number = number;
    }
    public String getdesig()
    {
        return desig;
    }

    public void setdesig(String desig)
    {
        this.desig = desig;
    }
}
