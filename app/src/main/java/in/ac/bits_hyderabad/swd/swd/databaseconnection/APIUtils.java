package in.ac.bits_hyderabad.swd.swd.databaseconnection;

public class APIUtils {
    private APIUtils() {}

    public static final String BASE_URL = "http://swd.bits-hyderabad.ac.in/app/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }
}
