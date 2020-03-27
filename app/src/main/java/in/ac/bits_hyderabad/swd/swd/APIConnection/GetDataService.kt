package `in`.ac.bits_hyderabad.swd.swd.APIConnection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

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

    @PUT("passmail.php/")
    fun putPasswordResetRequest(@Query("id") uid: String, @Query("reset") reset: String): Call<Login>

}