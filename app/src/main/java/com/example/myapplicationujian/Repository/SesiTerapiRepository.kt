package com.example.myapplicationujian.Repository

import com.example.myapplicationujian.Model.SesiTerapi
import com.example.myapplicationujian.Model.SesiTerapiResponse
import com.example.myapplicationujian.Service.SesiTerapiService
import java.io.IOException

interface SesiTerapiRepository {
    suspend fun getAllSesiTerapi(): SesiTerapiResponse

    suspend fun insertSesiTerapi(sesiTerapi: SesiTerapi)

    suspend fun updateSesiTerapi(idSesi: Int, sesiTerapi: SesiTerapi)

    suspend fun deleteSesiTerapi(idSesi: Int)

    suspend fun getSesiTerapiById(idSesi: Int): SesiTerapi
}

class NetworkSesiTerapiRepository(
    private val sesiTerapiApiService: SesiTerapiService
) : SesiTerapiRepository {

    override suspend fun getAllSesiTerapi(): SesiTerapiResponse =
        sesiTerapiApiService.getSesiTerapi()

    override suspend fun insertSesiTerapi(sesiTerapi: SesiTerapi) {
        sesiTerapiApiService.insertSesiTerapi(sesiTerapi)
    }

    override suspend fun updateSesiTerapi(idSesi: Int, sesiTerapi: SesiTerapi) {
        sesiTerapiApiService.updateSesiTerapi(idSesi, sesiTerapi)
    }

    override suspend fun deleteSesiTerapi(idSesi: Int) {
        try {
            val response = sesiTerapiApiService.deleteSesiTerapi(idSesi)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete SesiTerapi. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSesiTerapiById(idSesi: Int): SesiTerapi {
        return sesiTerapiApiService.getSesiTerapiById(idSesi).data
    }
}
