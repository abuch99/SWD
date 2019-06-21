package in.ac.bits_hyderabad.swd.swd.databaseconnection;


import java.util.List;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.databaseconnection.responseclasses.AuthenticationData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface APIService {

    @POST("index.php")

    @FormUrlEncoded
    Call<ResponseBody> loginUser(@Field("tag") String login,
                                 @Field("id")String id,
                                 @Field("pwd")String pwd);

    @POST("docgen.php")
    @FormUrlEncoded
    Call<AuthenticationData> bonafideAdmin(@Field("enterid") String enter_id,
                                           @Field("bonafide") String bonafide);
//
//    @POST("register_user.php")
//    @FormUrlEncoded
//    Call<AuthenticationData> registerUser(@Field("mobile_number") String mobile_number,
//                                          @Field("password") String password);
//
//    @POST("check_mobile.php")
//    @FormUrlEncoded
//    Call<AuthenticationData> checkMobile(@Field("mobile_number") String mobile_number);
//
//    @POST("forgotpassword_user.php")
//    @FormUrlEncoded
//    Call<AuthenticationData> changePassword(@Field("mobile_number") String user_id,
//                                            @Field("password") String password);
//    @POST("personal_form.php")
//    @FormUrlEncoded
//    Call<ResponseData> savePersonal(@Field("user_id") String user_id,
//                                    @Field("personal_name") String personal_name,
//                                    @Field("personal_dob") String personal_dob,
//                                    @Field("personal_gender") String personal_gender,
//                                    @Field("personal_address") String personal_address,
//                                    @Field("personal_place") String personal_place,
//                                    @Field("personal_district") String personal_district);
//    @POST("family_form.php")
//    @FormUrlEncoded
//    Call<ResponseData> saveFamily(@Field("user_id") String user_id,
//                                  @Field("family_name") String family_name,
//                                  @Field("family_age") String family_age,
//                                  @Field("family_gender") String family_gender,
//                                  @Field("family_occupation") String family_occupation,
//                                  @Field("family_relationship") String family_relationship);
//
//    @POST("trading_form.php")
//    @FormUrlEncoded
//    Call<ResponseData> saveTrading(@Field("user_id") String user_id,
//                                   @Field("trading_firm_name") String trading_firm_name,
//                                   @Field("trading_firm_address") String trading_firm_address,
//                                   @Field("trading_firm_turnover") String trading_firm_turnover,
//                                   @Field("trading_mtp_branch") String trading_mtp_branch,
//                                   @Field("trading_mtp_godown") String trading_mtp_godown,
//                                   @Field("trading_mtp_factory") String trading_mtp_factory,
//                                   @Field("trading_mtp_others") String trading_mtp_others,
//                                   @Field("trading_ownership_type") String trading_mtp_ownership_type,
//                                   @Field("trading_capital_contribution") String trading_capital_contribution,
//                                   @Field("trading_gstn_date") String trading_gstn_date,
//                                   @Field("trading_license_num") String trading_license_num,
//                                   @Field("trading_license_authority") String trading_license_authority,
//                                   @Field("trading_official_name") String trading_official_name);
//
//
//
//    @POST("app_status_check.php")
//    @FormUrlEncoded
//    Call<List<ClaimsData>> checkStatus(@Field("user_id") String user_id);
//
//    @POST("other_details.php")
//    @FormUrlEncoded
//    Call<ResponseData> saveOther(@Field("user_id") String user_id,
//                                 @Field("emv_main_branch") String emv_main_branch,
//                                 @Field("emv_branch") String emv_branch,
//                                 @Field("emv_godown") String emv_godown,
//                                 @Field("emv_factory") String emv_factory,
//                                 @Field("emv_others") String emv_others,
//                                 @Field("ara_main_branch") String ara_main_branch,
//                                 @Field("ara_branch") String ara_branch,
//                                 @Field("ara_godown") String ara_godown,
//                                 @Field("ara_factory") String ara_factory,
//                                 @Field("ara_other") String ara_other,
//                                 @Field("organisation_name") String organisation_name);
//
//
//    @POST("banking_form.php")
//    @FormUrlEncoded
//    Call<ResponseData> saveBanking(@Field("user_id") String user_id,
//                                   @Field("bank_name") String bank_name,
//                                   @Field("bank_account_num") String bank_account_num,
//                                   @Field("account_holder_name") String account_holder_name,
//                                   @Field("branch_name") String branch_name,
//                                   @Field("bank_ifsc") String bank_ifsc);
//
//    @POST("class_change_form.php")
//    @FormUrlEncoded
//    Call<TurnoverData> getTurnoverData(@Field("user_id") String user_id);
//
//
//    @POST("get_personal_data.php")
//    @FormUrlEncoded
//    Call<PersonalData> getPersonalData(@Field("mobile_number") String mobile_number,
//                                       @Field("password") String password);
//
//    @POST("get_family_data.php")
//    @FormUrlEncoded
//    Call<List<FamilyData>> getFamilyData(@Field("mobile_number") String mobile_number,
//                                         @Field("password") String password);
//
//    @POST("get_trading_data.php")
//    @FormUrlEncoded
//    Call<TradingData> getTradingData(@Field("mobile_number") String mobile_number,
//                                     @Field("password") String password);
//
//
//    @POST("get_other_data.php")
//    @FormUrlEncoded
//    Call<OtherData> getOtherData(@Field("mobile_number") String mobile_number,
//                                 @Field("password") String password);
//
//
//    @POST("get_banking_details.php")
//    @FormUrlEncoded
//    Call<BankingData> getBankingData(@Field("mobile_number") String mobile_number,
//                                     @Field("password") String password);
}
