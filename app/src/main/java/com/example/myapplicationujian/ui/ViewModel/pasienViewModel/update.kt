package com.example.myapplicationujian.ui.ViewModel.pasienViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationujian.Model.Pasien
import com.example.myapplicationujian.Repository.PasienRepository
import com.example.myapplicationujian.ui.View.PasienView.DestinasiUpdate
import kotlinx.coroutines.launch


class UpdatePasienViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienRepository: PasienRepository
) : ViewModel() {
    val idPasien: Int = checkNotNull(savedStateHandle[DestinasiUpdate.idpasien])

    var uiState by mutableStateOf(InsertPasienUiState())
        private set

    init {
        getPasien()
    }

    // Fungsi untuk mengambil data pasien
    private fun getPasien() {
        viewModelScope.launch {
            try {
                val pasien = pasienRepository.getPasienById(idPasien)
                pasien?.let {
                    uiState = it.toUiStatePasien() // Update UI State dengan data pasien
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk melakukan update pasien
    fun updatePasien(idPasien: Int, pasien: Pasien) {
        viewModelScope.launch {
            try {
                pasienRepository.updatePasien(idPasien, pasien) // Panggil repository untuk update
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk memperbarui UI State
    fun updatePasienState(insertUiEvent: InsertPasienUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }
}