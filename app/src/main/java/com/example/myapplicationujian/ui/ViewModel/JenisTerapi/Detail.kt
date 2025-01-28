package com.example.myapplicationujian.ui.ViewModel.JenisTerapi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.Repository.JenisTerapiRepository
import com.example.myapplicationujian.ui.View.JenisTerapi.DestinasiDetailJenisTerapi
import com.example.myapplicationujian.ui.View.TerapisView.DestinasiDetailTerapis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// UI State untuk DetailJenisTerapi
sealed class DetailJenisTerapiUiState {
    data class Success(val jenisTerapi: JenisTerapi) : DetailJenisTerapiUiState()
    object Error : DetailJenisTerapiUiState()
    object Loading : DetailJenisTerapiUiState()
}

class DetailJenisTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisTerapiRepo: JenisTerapiRepository
) : ViewModel() {
    private val _idJenisTerapi: Int = checkNotNull(savedStateHandle[DestinasiDetailJenisTerapi.idJenisTerapi])

    // Mengambil ID Jenis Terapi dari SavedStateHandle dengan penanganan error yang lebih baik
        ?: throw IllegalArgumentException("ID Jenis Terapi tidak ditemukan dalam SavedStateHandle")

    // StateFlow untuk mengelola UI State
    private val _detailJtUiState = MutableStateFlow<DetailJenisTerapiUiState>(DetailJenisTerapiUiState.Loading)
    val detailUiState: StateFlow<DetailJenisTerapiUiState> = _detailJtUiState

    // Inisialisasi ViewModel dan mengambil detail Jenis Terapi
    init {
        getDetailJenisTerapi()
    }

    // Fungsi untuk mengambil detail Jenis Terapi berdasarkan ID
    fun getDetailJenisTerapi() {
        viewModelScope.launch {
            try {
                _detailJtUiState.value = DetailJenisTerapiUiState.Loading
                val jenisTerapi = jenisTerapiRepo.getJenisTerapiById(_idJenisTerapi).firstOrNull()

                if (jenisTerapi != null) {
                    _detailJtUiState.value = DetailJenisTerapiUiState.Success(jenisTerapi)
                } else {
                    _detailJtUiState.value = DetailJenisTerapiUiState.Error
                }
            } catch (e: Exception) {
                // Log or handle the exception if needed
                _detailJtUiState.value = DetailJenisTerapiUiState.Error
            }
        }
    }
}
