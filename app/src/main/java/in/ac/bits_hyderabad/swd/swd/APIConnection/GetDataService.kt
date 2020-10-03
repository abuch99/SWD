package `in`.ac.bits_hyderabad.swd.swd.APIConnection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GetDataService {

    @GET("swd_app/")
    fun getGoodies(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String): Call<ArrayList<Goodie>>

    @GET("swd_app/")
    fun getDeductions(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String): Call<ArrayList<Deduction>>

    @GET("swd_app/")
    fun getMessMenu(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String): Call<ArrayList<MessMenu>>

    @GET("swd_app/")
    fun getMessReq(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String): Call<MessReq>

    @GET("swd_app/")
    fun getLoginSuccessful(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String): Call<Login>

    @GET("swd_app/")
    fun getUpdateLoginResponse(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String, @QueryMap params: Map<String, String>): Call<UpdateLoginResponse>

    @GET("swd_app/gen_appDocs.php/")
    fun getDocContent(@Query("tag") tag: String, @Query("uid") uid: String, @Query("doc_type") docType: String): Call<DocContents>

    @GET("swd_app/")
    fun getGoodieOrderPlacedResponse(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String, @QueryMap params: Map<String, String>): Call<GoodieOrderPlacedResponse>

    @GET("mess/")
    fun getMessRegDetails(@Query("isapp") isapp: String): Call<MessReg>

    @GET("mess/")
    fun getMessRegResponse(@Query("isapp") isapp: String, @Query("user_name") uid: String, @Query("user_password") pwd: String, @Query("user_mess") messNo: String): Call<MessRegistrationResponse>

    @GET("passmail.php/")
    fun getPasswordResetResponse(@Query("id") uid: String, @Query("reset") reset: String): Call<Login>

    @GET("swd_app/")
    fun getConnectData(@Query("tag") tag: String): Call<ConnectData>

    companion object

}