package com.example.myapplicationujian.Repository

import com.example.myapplicationujian.Model.JenisTerapisResponse
import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.Service.JenisterapiService
import java.io.IOException

interface JenisTerapiRepository {
    suspend fun getJenisTerapi(): JenisTerapisResponse
    suspend fun insertJenisTerapi(jenisTerapi: JenisTerapi)
    suspend fun updateJenisTerapi(id_Jenis_Terapi: Int, jenisTerapi: JenisTerapi)
    suspend fun deleteJenisTerapi(id_Jenis_Terapi: Int)
    suspend fun getJenisTerapiById(id_Jenis_Terapi: Int): List<JenisTerapi> // Mengembalikan List<JenisTerapi>
}

class NetworkJenisTerapisRepository(
    private val JenisTerapiApiService: JenisterapiService
) : JenisTerapiRepository {
    override suspend fun getJenisTerapi(): JenisTerapisResponse =
        JenisTerapiApiService.getJenisTerapi()

    override suspend fun insertJenisTerapi(jenisTerapi: JenisTerapi) {
        JenisTerapiApiService.insertJenisTerapi(jenisTerapi)
    }

    override suspend fun updateJenisTerapi(id_Jenis_Terapi: Int, jenisTerapi: JenisTerapi) {
        JenisTerapiApiService.updateJenisTerapi(id_Jenis_Terapi, jenisTerapi)
    }

    override suspend fun deleteJenisTerapi(id_Jenis_Terapi: Int) {
        try {
            val response = JenisTerapiApiService.deleteJenisTerapi(id_Jenis_Terapi)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Jenis Terapi. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getJenisTerapiById(id_Jenis_Terapi: Int): List<JenisTerapi> {
        val response = JenisTerapiApiService.getJenisTerapiById(id_Jenis_Terapi)
        return listOf(response.data)
    }

}
