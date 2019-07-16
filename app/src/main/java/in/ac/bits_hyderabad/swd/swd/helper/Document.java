package in.ac.bits_hyderabad.swd.swd.helper;

public class Document {

    private String name;
    private String url;

    public Document (String name, String url){
        this.name=name;
        this.url=url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
