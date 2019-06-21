package in.ac.bits_hyderabad.swd.swd.databaseconnection.responseclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticationData {
    @SerializedName("response_code")
    @Expose
    private int responseCode;
    @SerializedName("id")
    @Expose
    private String id;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
