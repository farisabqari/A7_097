package com.example.myapplicationujian.ui.ViewModel.JenisTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.Repository.JenisTerapiRepository
import kotlinx.coroutines.launch

class InsertJenisTerapiViewModel(private val jenisTerapiRepo: JenisTerapiRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertJenisTerapiUiState())
        private set

    // Fungsi untuk memperbarui state form
    fun updateInsertJenisTerapiState(insertUiEvent: InsertJenisTerapiUiEvent) {
        uiState = InsertJenisTerapiUiState(insertUiEvent = insertUiEvent)
    }

    // Fungsi untuk menyimpan data jenis terapi
    fun insertJenisTerapi(onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val jenisTerapi = uiState.insertUiEvent.toJenisTerapi()
                jenisTerapiRepo.insertJenisTerapi(jenisTerapi)
                onSuccess() // Callback jika berhasil
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e) // Callback untuk menangani error
            }
        }
    }
}



// State untuk UI form
data class InsertJenisTerapiUiState(
    val insertUiEvent: InsertJenisTerapiUiEvent = InsertJenisTerapiUiEvent()
)

// Event untuk mengelola input form
data class InsertJenisTerapiUiEvent(
    val idJenisTerapi: Int = 0, // Default ke 0, biasanya ID di-generate oleh server
    val namaJenisTerapi: String = "",
    val deskripsiTerapi: String = ""
)

// Fungsi untuk mengubah InsertJenisTerapiUiEvent menjadi objek JenisTerapi
fun InsertJenisTerapiUiEvent.toJenisTerapi(): JenisTerapi = JenisTerapi(
    id_jenis_terapi = idJenisTerapi,
    nama_jenis_terapi = namaJenisTerapi,
    deskripsi_terapi = deskripsiTerapi
)

// Fungsi untuk mengubah objek JenisTerapi ke UI State
fun JenisTerapi.toUiStateJenisTerapi(): InsertJenisTerapiUiState = InsertJenisTerapiUiState(
    insertUiEvent = toInsertJenisTerapiUiEvent()
)

// Fungsi untuk mengubah objek JenisTerapi ke InsertJenisTerapiUiEvent
fun JenisTerapi.toInsertJenisTerapiUiEvent(): InsertJenisTerapiUiEvent = InsertJenisTerapiUiEvent(
    idJenisTerapi = id_jenis_terapi,
    namaJenisTerapi = nama_jenis_terapi,
    deskripsiTerapi = deskripsi_terapi
)
