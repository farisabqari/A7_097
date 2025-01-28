package com.example.myapplicationujian.ui.ViewModel.TerapiViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationujian.Model.Terapis
import com.example.myapplicationujian.Repository.TerapisRepository
import com.example.myapplicationujian.ui.View.TerapisView.DestinasiDetailTerapis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailTerapisUiState {
    data class Success(val terapis: Terapis) : DetailTerapisUiState()
    object Error : DetailTerapisUiState()
    object Loading : DetailTerapisUiState()
}

class DetailTerapisViewModel(
    savedStateHandle: SavedStateHandle,
    private val terapisRepo: TerapisRepository
) : ViewModel() {

    private val _idTerapis: Int = checkNotNull(savedStateHandle[DestinasiDetailTerapis.idTerapis])

    private val _detailUiState = MutableStateFlow<DetailTerapisUiState>(DetailTerapisUiState.Loading)
    val detailUiState: StateFlow<DetailTerapisUiState> = _detailUiState

    init {
        getDetailTerapis()
    }

    fun getDetailTerapis() {
        viewModelScope.launch {
            try {
                _detailUiState.value = DetailTerapisUiState.Loading
                val terapis = terapisRepo.getTerapisById(_idTerapis)
                if (terapis != null) {
                    _detailUiState.value = DetailTerapisUiState.Success(terapis)
                } else {
                    _detailUiState.value = DetailTerapisUiState.Error
                }
            } catch (e: Exception) {
                _detailUiState.value = DetailTerapisUiState.Error
            }
        }
    }
}

fun Terapis.toDetailUiEvent(): InsertTerapisUiEvent {
    return InsertTerapisUiEvent(
        idTerapis = id_terapis,
        namaTerapis = nama_terapis,
        spesialisasi = spesialisasi,
        nomorIzinPraktik = nomor_izin_praktik
    )
}