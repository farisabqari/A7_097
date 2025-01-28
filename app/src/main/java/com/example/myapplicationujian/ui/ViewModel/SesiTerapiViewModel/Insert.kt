package com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationujian.Model.*
import com.example.myapplicationujian.Repository.*
import kotlinx.coroutines.launch

class InsertSesiViewModel(
    private val sesiRepository: SesiTerapiRepository,
    private val pasienRepository: PasienRepository,
    private val terapisRepository: TerapisRepository,
    private val jenisTerapiRepository: JenisTerapiRepository
) : ViewModel() {

    var stuiState by mutableStateOf(InsertSesiUiState())
        private set


    init {
        loadDropdownOptions()
    }

    private fun loadDropdownOptions() {
        viewModelScope.launch {
            try {


                val pasienResponse: PasienResponse = pasienRepository.getPasien()
                val pasienList: List<Pasien> = pasienResponse.data

                val terapisResponse: TerapisResponse = terapisRepository.getTerapis()
                val terapisList: List<Terapis> = terapisResponse.data

                val jenisTerapiResponse: JenisTerapisResponse = jenisTerapiRepository.getJenisTerapi()
                val jenisterapisList: List<JenisTerapi> = jenisTerapiResponse.data

                stuiState = stuiState.copy(
                    pasienOptions = pasienList.map {
                        it.toDropdownOptionPs()
                    },
                    terapisOptions = terapisList.map {
                        it.toDropdownOptionTs()
                    },
                    jenisTerapiOptions = jenisterapisList.map {
                        it.toDropdownOptionJt()
                    }
                )

            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateInsertState(event: InsertSesiUiEvent) {
        stuiState = stuiState.copy(insertstEvent = event)
    }

    fun insertSesiTerapi() {
        viewModelScope.launch {
            try {
                sesiRepository.insertSesiTerapi(stuiState.insertstEvent.toSesiTerapi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertSesiUiState(
    val insertstEvent: InsertSesiUiEvent = InsertSesiUiEvent(),
    val pasienOptions: List<DropdownOptionPs> = emptyList(),
    val terapisOptions: List<DropdownOptionTs> = emptyList(),
    val jenisTerapiOptions: List<DropdownOptionJt> = emptyList()
)

data class InsertSesiUiEvent(
    val idSesi: Int = 0,
    val idPasien: Int = 0,
    val idTerapis: Int = 0,
    val idJenisTerapi: Int = 0,
    val tanggalSesi: String = "",
    val catatanSesi: String = ""
)

fun InsertSesiUiEvent.toSesiTerapi() = SesiTerapi(
    id_sesi = idSesi,
    id_pasien = idPasien,
    id_terapis = idTerapis,
    id_jenis_terapi = idJenisTerapi,
    tanggal_sesi = tanggalSesi,
    catatan_sesi = catatanSesi
)

fun SesiTerapi.toUiState(): InsertSesiUiState = InsertSesiUiState(
    insertstEvent = toUiEvent()
)

fun SesiTerapi.toUiEvent() = InsertSesiUiEvent(
    idSesi = id_sesi,
    idPasien = id_pasien,
    idTerapis = id_terapis,
    idJenisTerapi = id_jenis_terapi,
    tanggalSesi = tanggal_sesi?:"Unknown",
    catatanSesi = catatan_sesi
)

data class DropdownOptionPs(
    val id_Pasien: Int,
    val label: String
)

data class DropdownOptionTs(
    val id_Terapis: Int,
    val label: String
)

data class DropdownOptionJt(
    val id_JenisTerapi: Int,
    val label: String
)

fun Pasien.toDropdownOptionPs() = DropdownOptionPs(
    id_Pasien = id_pasien,
    label = nama_pasien
)

fun Terapis.toDropdownOptionTs() = DropdownOptionTs(
    id_Terapis = id_terapis,
    label = nama_terapis
)

fun JenisTerapi.toDropdownOptionJt() = DropdownOptionJt(
    id_JenisTerapi = id_jenis_terapi,
    label = nama_jenis_terapi
)
