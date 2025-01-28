package com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationujian.Model.SesiTerapi
import com.example.myapplicationujian.Repository.SesiTerapiRepository
import kotlinx.coroutines.launch

sealed class DetailSesiUiState {
    data class Success(val sesiTerapi: SesiTerapi) : DetailSesiUiState()
    object Error : DetailSesiUiState()
    object Loading : DetailSesiUiState()
}
class DetailSesiTerapiViewModel (
    savedStateHandle: SavedStateHandle,

    private val sesiRepository: SesiTerapiRepository
): ViewModel(){
    var stUiState by mutableStateOf(DetailStUiState())
        private set

    fun fetchDetailSesi(id_sesiTerapi : Int) {
        viewModelScope.launch {
            stUiState = DetailStUiState(isLoading = true)
            try {
                val SesiTerapi = sesiRepository.getSesiTerapiById(id_sesiTerapi)

                stUiState = DetailStUiState(detailSesiUiEvent = SesiTerapi.toDetailMtgEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                stUiState = DetailStUiState    (isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class DetailStUiState(
    val detailSesiUiEvent: InsertSesiUiEvent = InsertSesiUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
){
    val isUiEventNotEmpty: Boolean
        get() = detailSesiUiEvent != InsertSesiUiEvent()
}

fun SesiTerapi.toDetailMtgEvent(): InsertSesiUiEvent {
    return InsertSesiUiEvent(
        idSesi = id_sesi,
        idPasien = id_pasien,
        idTerapis = id_terapis,
        idJenisTerapi = id_jenis_terapi,
        tanggalSesi = tanggal_sesi?:"Unknown",
        catatanSesi = catatan_sesi
    )
}