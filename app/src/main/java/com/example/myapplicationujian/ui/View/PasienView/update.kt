package com.example.myapplicationujian.ui.View.PasienView

import com.example.myapplicationujian.R
import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.pasienViewModel.UpdatePasienViewModel
import com.example.myapplicationujian.ui.ViewModel.pasienViewModel.toPasien
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update pasien"
    const val idpasien = "idpasien"
    val routesWithArg = "$route/{$idpasien}"
    override val titleRes = "Update Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasienView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiState = viewModel.uiState

    // Format tanggal lahir yang diterima (UTC format)
    val formattedTanggalLahir = remember(uiState.insertUiEvent?.tanggalLahir) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(uiState.insertUiEvent?.tanggalLahir ?: "")
            parsedDate?.let { format.format(it) } ?: uiState.insertUiEvent?.tanggalLahir // Handling null or invalid date
        } catch (e: Exception) {
            uiState.insertUiEvent?.tanggalLahir ?: "" // If parsing fails, keep the original or empty string
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image with optional Blur Effect
            Image(
                painter = painterResource(id = R.drawable.log), // Replace with your image
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { // Optional: Add effects like blur or shadow if needed
                        shadowElevation = 8f
                    }
            )
            // Main Content (Form)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // For scrolling the form
            ) {
                // Entry Pasien Form Body
                EntryBodyPasien(
                    insertUiState = uiState.copy(
                        insertUiEvent = uiState.insertUiEvent?.copy(tanggalLahir = formattedTanggalLahir) ?: uiState.insertUiEvent
                    ),
                    onPasienValueChange = { updatedValue ->
                        viewModel.updatePasienState(updatedValue)
                    },
                    onSaveClick = {
                        uiState.insertUiEvent?.let { insertUiEvent ->
                            coroutineScope.launch {
                                viewModel.updatePasien(
                                    idPasien = viewModel.idPasien,
                                    pasien = insertUiEvent.toPasien()
                                )
                                navigateBack() // Navigate back after saving
                            }
                        }
                    }
                )
            }
        }
    }
}
