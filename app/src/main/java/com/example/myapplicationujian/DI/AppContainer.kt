package com.example.myapplicationujian.DI

import com.example.myapplicationujian.Repository.JenisTerapiRepository
import com.example.myapplicationujian.Repository.NetworkJenisTerapisRepository
import com.example.myapplicationujian.Repository.NetworkPasienRepository
import com.example.myapplicationujian.Repository.NetworkSesiTerapiRepository
import com.example.myapplicationujian.Repository.NetworkTerapisRepository
import com.example.myapplicationujian.Repository.PasienRepository
import com.example.myapplicationujian.Repository.SesiTerapiRepository
import com.example.myapplicationujian.Repository.TerapisRepository
import com.example.myapplicationujian.Service.JenisterapiService
import com.example.myapplicationujian.Service.PasienService
import com.example.myapplicationujian.Service.SesiTerapiService
import com.example.myapplicationujian.Service.TerapisService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pasienRepository: PasienRepository
    val terapisRepository: TerapisRepository
    val jenisTerapiRepository: JenisTerapiRepository
    val sesiTerapiRepository: SesiTerapiRepository


}

class KlinikContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json { ignoreUnknownKeys = true }

    // Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    // Pasien service and repository
    private val pasienService: PasienService by lazy {
        retrofit.create(PasienService::class.java)
    }
    override val pasienRepository: PasienRepository by lazy {
        NetworkPasienRepository(pasienService)
    }

    // Terapis service and repository
    private val terapisService: TerapisService by lazy {
        retrofit.create(TerapisService::class.java)
    }
    override val terapisRepository: TerapisRepository by lazy {
        NetworkTerapisRepository(terapisService)
    }

    private val jenisTerapisService: JenisterapiService by lazy {
        retrofit.create(JenisterapiService::class.java)
    }
    override val jenisTerapiRepository: JenisTerapiRepository by lazy {
        NetworkJenisTerapisRepository(jenisTerapisService)
    }

    private val sesiTerapiService: SesiTerapiService by lazy {
        retrofit.create(SesiTerapiService::class.java)
    }
    override val sesiTerapiRepository: SesiTerapiRepository by lazy {
        NetworkSesiTerapiRepository(sesiTerapiService)
    }



}
