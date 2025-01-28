package com.example.myapplicationujian.Service

import com.example.myapplicationujian.Model.Terapis
import com.example.myapplicationujian.Model.TerapisDetailResponse
import com.example.myapplicationujian.Model.TerapisResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TerapisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("terapis/")
    suspend fun getAllTerapis(): TerapisResponse

    @GET("terapis/{id_terapis}")
    suspend fun getTerapisById(@Path("id_terapis") idTerapis: Int): TerapisDetailResponse

    @POST("terapis/store")
    suspend fun insertTerapis(@Body terapis: Terapis)

    @PUT("terapis/{id_terapis}")
    suspend fun updateTerapis(@Path("id_terapis") idTerapis: Int, @Body terapis: Terapis)

    @DELETE("terapis/{id_terapis}")
    suspend fun deleteTerapis(@Path("id_terapis") idTerapis: Int): Response<Void>
}