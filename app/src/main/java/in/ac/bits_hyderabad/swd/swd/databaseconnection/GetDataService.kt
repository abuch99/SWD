package `in`.ac.bits_hyderabad.swd.swd.databaseconnection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {

    @GET("swd_app/")
    fun getMessMenu(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String): Call<ArrayList<MessMenu>>

    @GET("swd_app/")
    fun getMessReq(@Query("tag") tag: String, @Query("id") id: String, @Query("pwd") password: String): Call<MessReq>

}