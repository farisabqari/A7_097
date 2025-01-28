package com.example.myapplicationujian.Repository

import com.example.myapplicationujian.Model.PasienResponse
import com.example.myapplicationujian.Model.Pasien
import com.example.myapplicationujian.Service.PasienService
import okio.IOException

interface PasienRepository {
    suspend fun getPasien(): PasienResponse
    suspend fun getAllPasien(): PasienResponse

    suspend fun insertPasien(pasien: Pasien)

    suspend fun updatePasien(idPasien: Int, pasien: Pasien)

    suspend fun deletePasien(idPasien: Int)

    suspend fun getPasienById(idPasien: Int): Pasien
}

class NetworkPasienRepository(
    private val pasienApiService: PasienService
) : PasienRepository {
    override suspend fun getPasien(): PasienResponse =
        pasienApiService.getAllPasien()

    override suspend fun getAllPasien(): PasienResponse =
        pasienApiService.getAllPasien()

    override suspend fun insertPasien(pasien: Pasien) {
        pasienApiService.insertPasien(pasien)
    }

    override suspend fun updatePasien(idPasien: Int, pasien: Pasien) {
        pasienApiService.updatePasien(idPasien, pasien)
    }

    override suspend fun deletePasien(idPasien: Int) {
        try {
            val response = pasienApiService.deletePasien(idPasien)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Pasien. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPasienById(idPasien: Int): Pasien {
        return pasienApiService.getPasienById(idPasien).data
    }
}