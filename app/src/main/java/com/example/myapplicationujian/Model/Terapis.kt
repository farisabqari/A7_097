package com.example.myapplicationujian.Model

import kotlinx.serialization.*

@Serializable
data class Terapis(
    val id_terapis: Int,
    val nama_terapis: String,
    val spesialisasi: String,
    val nomor_izin_praktik: String
)

@Serializable
data class TerapisResponse(
    val status: Boolean,
    val message: String,
    val data: List<Terapis>
)

@Serializable
data class TerapisDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Terapis
)