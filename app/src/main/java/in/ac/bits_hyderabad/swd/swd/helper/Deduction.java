package in.ac.bits_hyderabad.swd.swd.helper;

public class Deduction {

    String id;
    String name;
    String amount;
    String xs;
    String s;
    String m;
    String l;
    String xl;
    String xxl;
    String xxxl;
    String qut;
    String type;
    String netqut;


    public Deduction( String type, String id, String name, String amount,String xs,String s,String m,String l,String xl,String xxl, String xxxl,String qut, String netqut){
        this.name=name;
        this.xs=xs;
        this.type=type;
        this.s=s;
        this.id=id;
        this.m=m;
        this.l=l;
        this.xl=xl;
        this.xxl=xxl;
        this.xxxl=xxxl;
        this.qut=qut;
        this.netqut=netqut;
        this.amount=amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXs() {
        return xs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getNetqut() {
        return netqut;
    }

    public void setNetqut(String netqut) {
        this.netqut = netqut;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
