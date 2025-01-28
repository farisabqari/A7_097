package com.example.myapplicationujian.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.serialization.Serializable


@Entity(
    tableName = "monitoring",
    foreignKeys = [
        ForeignKey(
            entity = Pasien::class,
            parentColumns = ["id_Pasien"],
            childColumns = ["id_Pasien"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Terapis::class,
            parentColumns = ["id_Terapis"],
            childColumns = ["id_Terapis"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = JenisTerapi::class,
            parentColumns = ["id_JenisTerapi"],
            childColumns = ["id_JenisTerapi"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Serializable
data class SesiTerapi(
    val id_sesi: Int  = 0,
    val id_pasien: Int = 0,
    val id_terapis: Int  = 0,
    val id_jenis_terapi: Int  = 0,
    val tanggal_sesi: String? = null,
    val catatan_sesi: String
)

@Serializable
data class SesiTerapiResponse(
    val status: Boolean,
    val message: String,
    val data: List<SesiTerapi>
)

@Serializable
data class SesiTerapiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: SesiTerapi
)
