package com.example.myapplicationujian.ui.ViewModel.JenisTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.Repository.JenisTerapiRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Representasi state UI untuk HomeJenisTerapis
sealed class HomeJTUiState {
    data class Success(val jenisTerapi: List<JenisTerapi>) : HomeJTUiState()  // Menggunakan List<JenisTerapi>
    object Error : HomeJTUiState()
    object Loading : HomeJTUiState()
}

// ViewModel untuk HomeJenisTerapis
class HomeJenisTerapisViewModel(private val jenisterapirepo: JenisTerapiRepository) : ViewModel() {

    // State untuk mengelola UI
    var jterapisUIState: HomeJTUiState by mutableStateOf(HomeJTUiState.Loading)
        private set

    init {
        getJenisTerapi()
    }

    // Mendapatkan data jenis terapi
    fun getJenisTerapi() {
        viewModelScope.launch {
            jterapisUIState = HomeJTUiState.Loading // Set state ke Loading
            jterapisUIState = try {
                val response = jenisterapirepo.getJenisTerapi()
                // Mengubah data yang diterima menjadi list of JenisTerapi
                HomeJTUiState.Success(response.data) // Langsung kirim list response.data tanpa diubah
            } catch (e: IOException) {
                HomeJTUiState.Error
            } catch (e: HttpException) {
                HomeJTUiState.Error
            }
        }
    }

    // Menghapus jenis terapi berdasarkan ID
    fun deleteJenisTerapi(idJenisTerapi: Int) {
        viewModelScope.launch {
            try {
                jenisterapirepo.deleteJenisTerapi(idJenisTerapi)
                getJenisTerapi() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                jterapisUIState = HomeJTUiState.Error
            } catch (e: HttpException) {
                jterapisUIState = HomeJTUiState.Error
            }
        }
    }
}
