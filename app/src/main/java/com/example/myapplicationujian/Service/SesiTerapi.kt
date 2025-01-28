package com.example.myapplicationujian.Service

import com.example.myapplicationujian.Model.SesiTerapi
import com.example.myapplicationujian.Model.SesiTerapiDetailResponse
import com.example.myapplicationujian.Model.SesiTerapiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SesiTerapiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("sesiterapi/")
    suspend fun getSesiTerapi(): SesiTerapiResponse

    @GET("sesiterapi/{id_sesi}")
    suspend fun getSesiTerapiById(@Path("id_sesi") idSesi: Int): SesiTerapiDetailResponse

    @POST("sesiterapi/store")
    suspend fun insertSesiTerapi(@Body sesiTerapi: SesiTerapi)

    @PUT("sesiterapi/{id_sesi}")
    suspend fun updateSesiTerapi(@Path("id_sesi") idSesi: Int, @Body sesiTerapi: SesiTerapi)

    @DELETE("sesiterapi/{id_sesi}")
    suspend fun deleteSesiTerapi(@Path("id_sesi") idSesi: Int): Response<Void>
}
