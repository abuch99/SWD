package in.ac.bits_hyderabad.swd.swd.databaseconnection;

public class DisplayErrorMessage {
    public static String returnErrorMessage(int code) {
        // 200 for correct response, 400 for incorrect response
        switch (code) {
            case 200:
                return "Request successful!";
            case 400:
                return "Empty parameters";
            case 401:
                return "Invalid Inputs";
            case 402:
                return "SQL query error";
            case 403:
                return "No matching values in DB";
                default:
                    return "Unknown error";
        }
    }

    public static String returnUserPrompts(int code) {
        // 1- for authentication
        // 2- for incorrect text input
        switch (code) {
            case 10:
                return "Authenticated!";
            case 11:
                return "Registered!";
            case 12:
                return "Password changed!";
            case 20:
                return "Submitting details!";
            case 21:
                return "Form fields are blank";
            case 22:
                return "Form fields have incorrect values";
                default:
                    return "Unknown error";
        }
    }
}
