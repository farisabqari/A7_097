package com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationujian.Repository.SesiTerapiRepository
import com.example.myapplicationujian.ui.View.SesiTerapiView.DestinasiUpdateSesi
import kotlinx.coroutines.launch

class UpdateSesiTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val sesiRepository: SesiTerapiRepository
): ViewModel() {
    var stupdateUiState by mutableStateOf(InsertSesiUiState())
        private set

    private val _idSesi: Int = checkNotNull(savedStateHandle[DestinasiUpdateSesi.idSesi])

    init {
        viewModelScope.launch {
            stupdateUiState = sesiRepository.getSesiTerapiById(_idSesi.toInt())
                .toUiState()
        }
    }

    fun updateInsertstState(insertsTUiEvent: InsertSesiUiEvent) {
        stupdateUiState = InsertSesiUiState(insertstEvent = insertsTUiEvent)
    }

    suspend fun updateSt() {
        viewModelScope.launch {
            try {
                sesiRepository.updateSesiTerapi(_idSesi.toInt(), stupdateUiState.insertstEvent.toSesiTerapi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}