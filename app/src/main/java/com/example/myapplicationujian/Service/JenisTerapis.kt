package com.example.myapplicationujian.Service

import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.Model.JenisTerapiDetailResponse
import com.example.myapplicationujian.Model.JenisTerapisResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JenisterapiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("jenisterapi/")
    suspend fun getJenisTerapi(): JenisTerapisResponse

    @GET("jenisterapi/{id_jenis_terapi}")
    suspend fun getJenisTerapiById(@Path("id_jenis_terapi") id_jenis_terapi: Int): JenisTerapiDetailResponse

    @POST("jenisterapi/store")
    suspend fun insertJenisTerapi(@Body jenisTerapi: JenisTerapi)

    @PUT("jenisterapi/{id_jenis_terapi}")
    suspend fun updateJenisTerapi(@Path("id_jenis_terapi") id_jenis_terapi: Int, @Body jenisTerapi: JenisTerapi)

    @DELETE("jenisterapi/{id_jenis_terapi}")
    suspend fun deleteJenisTerapi(@Path("id_jenis_terapi") id_jenis_terapi: Int): Response<Void>
}