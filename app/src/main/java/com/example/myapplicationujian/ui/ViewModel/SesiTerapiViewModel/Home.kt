package com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplicationujian.Model.SesiTerapi
import com.example.myapplicationujian.Repository.SesiTerapiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeStUiState{
    data class Success(val SesiTerapi: List<SesiTerapi>): HomeStUiState()
    object Error: HomeStUiState()
    object Loading: HomeStUiState()

}

class HomeSesiTerapiViewModel(
    private val sesiRepository: SesiTerapiRepository
): ViewModel() {
    var stUiState: HomeStUiState by mutableStateOf(HomeStUiState.Loading)
        private set

    init {
        getsesi()
    }

    fun getsesi() {
        viewModelScope.launch {
            stUiState = HomeStUiState.Loading
            stUiState = try {
                HomeStUiState.Success(sesiRepository.getAllSesiTerapi().data)
            } catch (e: IOException) {
                HomeStUiState.Error

            } catch (e: HttpException) {
                HomeStUiState.Error
            }
        }
    }

    fun deletSesi(id_SesiTerapi: Int) {
        viewModelScope.launch {
            try {
                sesiRepository.deleteSesiTerapi(id_SesiTerapi)
            } catch (e: IOException) {
                HomeStUiState.Error
            } catch (e: HttpException) {
                HomeStUiState.Error
            }
        }
    }
}