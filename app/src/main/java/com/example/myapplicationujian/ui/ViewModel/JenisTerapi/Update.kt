package com.example.myapplicationujian.ui.ViewModel.JenisTerapi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.Repository.JenisTerapiRepository
import com.example.myapplicationujian.ui.View.JenisTerapi.DestinasiUpdateJenisTerapi
import com.example.myapplicationujian.ui.View.TerapisView.DestinasiUpdateTerapis
import kotlinx.coroutines.launch

class UpdateJenisTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisTerapiRepo: JenisTerapiRepository
) : ViewModel() {

    val idJenisTerapi: Int = checkNotNull(savedStateHandle[DestinasiUpdateJenisTerapi.idJenisTerapi])

    var uiState = mutableStateOf(UpdateJenisTerapiUiState())

    init {
        getJenisTerapi()
    }

    // Fungsi untuk mendapatkan data Jenis Terapi berdasarkan ID
    private fun getJenisTerapi() {
        viewModelScope.launch {
            try {
                val response = jenisTerapiRepo.getJenisTerapiById(idJenisTerapi)
                val jenisTerapi = response.firstOrNull() // Mengambil item pertama jika ada
                jenisTerapi?.let {
                    uiState.value = it.toUiState() // Mengupdate state dengan data yang diperoleh
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Menangani error (misalnya, Anda bisa memperbarui state untuk menampilkan pesan error)
            }
        }
    }

    // Fungsi untuk memperbarui data Jenis Terapi
    fun updateJenisTerapi(jenisTerapi: JenisTerapi) {
        viewModelScope.launch {
            try {
                jenisTerapiRepo.updateJenisTerapi(idJenisTerapi, jenisTerapi)
                // Setelah update, Anda bisa mengubah UI State menjadi sukses atau lainnya
            } catch (e: Exception) {
                e.printStackTrace()
                // Tangani error jika perlu
            }
        }
    }

    // Fungsi untuk memperbarui UI State dengan event baru
    fun updateJenisTerapiState(updateUiEvent: UpdateJenisTerapiUiEvent) {
        uiState.value = uiState.value.copy(updateUiEvent = updateUiEvent)
    }
}


// Data class untuk UI State
data class UpdateJenisTerapiUiState(
    val updateUiEvent: UpdateJenisTerapiUiEvent = UpdateJenisTerapiUiEvent()
)

// Data class untuk Event Update Jenis Terapi
data class UpdateJenisTerapiUiEvent(
    val idJenisTerapi: Int = 0,
    val namaJenisTerapi: String = "",
    val deskripsiJenisTerapi: String = ""
)

// Extension function untuk mengubah JenisTerapi menjadi UI State
fun JenisTerapi.toUiState(): UpdateJenisTerapiUiState = UpdateJenisTerapiUiState(
    updateUiEvent = UpdateJenisTerapiUiEvent(
        idJenisTerapi = this.id_jenis_terapi,
        namaJenisTerapi = this.nama_jenis_terapi,
        deskripsiJenisTerapi = this.deskripsi_terapi
    )
)
