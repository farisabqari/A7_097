package com.example.myapplicationujian.Service

import com.example.myapplicationujian.Model.Pasien
import com.example.myapplicationujian.Model.PasienDetailResponse
import com.example.myapplicationujian.Model.PasienResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PasienService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("pasien/")
    suspend fun getAllPasien(): PasienResponse

    @GET("pasien/{id_pasien}")
    suspend fun getPasienById(@Path("id_pasien") idPasien: Int): PasienDetailResponse

    @POST("pasien/store")
    suspend fun insertPasien(@Body pasien: Pasien)

    @PUT("pasien/{id_pasien}")
    suspend fun updatePasien(@Path("id_pasien") idPasien: Int, @Body pasien: Pasien)

    @DELETE("pasien/{id_pasien}")
    suspend fun deletePasien(@Path("id_pasien") idPasien: Int): Response<Void>
}