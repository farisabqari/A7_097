package com.example.myapplicationujian.Repository

import com.example.myapplicationujian.Model.Terapis
import com.example.myapplicationujian.Model.TerapisResponse
import com.example.myapplicationujian.Service.TerapisService
import java.io.IOException

interface TerapisRepository {
    suspend fun getTerapis(): TerapisResponse

    suspend fun insertTerapis(terapis: Terapis)

    suspend fun updateTerapis(idTerapis: Int, terapis: Terapis)

    suspend fun deleteTerapis(idTerapis: Int)

    suspend fun getTerapisById(idTerapis: Int): Terapis
}

class NetworkTerapisRepository(
    private val terapisApiService: TerapisService
) : TerapisRepository {
    override suspend fun getTerapis(): TerapisResponse =
        terapisApiService.getAllTerapis()

    override suspend fun insertTerapis(terapis: Terapis) {
        terapisApiService.insertTerapis(terapis)
    }

    override suspend fun updateTerapis(idTerapis: Int, terapis: Terapis) {
        terapisApiService.updateTerapis(idTerapis, terapis)
    }

    override suspend fun deleteTerapis(idTerapis: Int) {
        try {
            val response = terapisApiService.deleteTerapis(idTerapis)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Terapis. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTerapisById(idTerapis: Int): Terapis {
        return terapisApiService.getTerapisById(idTerapis).data
    }
}