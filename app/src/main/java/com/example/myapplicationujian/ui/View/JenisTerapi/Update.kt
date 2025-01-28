package com.example.myapplicationujian.ui.View.JenisTerapi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.UpdateJenisTerapiUiEvent
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.UpdateJenisTerapiUiState
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.UpdateJenisTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.UpdateTerapisViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateJenisTerapi : DestinasiNavigasi {
    override val route = "update_jenis_terapi"
    const val idJenisTerapi = "id_jenis_terapi"
    val routeWithArg = "$route/{$idJenisTerapi}"
    override val titleRes = "Update Jenis Terapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBodyJenisTerapi(
    updateUiState: UpdateJenisTerapiUiState,
    onJenisTerapiValueChange: (UpdateJenisTerapiUiEvent) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Input untuk Nama Jenis Terapi
        TextField(
            value = updateUiState.updateUiEvent.namaJenisTerapi,
            onValueChange = { newValue ->
                onJenisTerapiValueChange(
                    updateUiState.updateUiEvent.copy(namaJenisTerapi = newValue) // Mengupdate namaJenisTerapi
                )
            },
            label = { Text("Nama Jenis Terapi") }
        )

        // Input untuk Deskripsi Jenis Terapi
        TextField(
            value = updateUiState.updateUiEvent.deskripsiJenisTerapi,
            onValueChange = { newValue ->
                onJenisTerapiValueChange(
                    updateUiState.updateUiEvent.copy(deskripsiJenisTerapi = newValue) // Mengupdate deskripsiJenisTerapi
                )
            },
            label = { Text("Deskripsi Jenis Terapi") }
        )

        // Tombol Simpan
        Button(
            onClick = onSaveClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateJenisTerapiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateJenisTerapi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            EntryBodyJenisTerapi(
                updateUiState = uiState,
                onJenisTerapiValueChange = { updateValue ->
                    viewModel.updateJenisTerapiState(updateValue)
                },
                onSaveClick = {
                    coroutineScope.launch {
                        val jenisTerapi = uiState.updateUiEvent.run {
                            JenisTerapi(
                                id_jenis_terapi = idJenisTerapi,
                                nama_jenis_terapi = namaJenisTerapi,
                                deskripsi_terapi = deskripsiJenisTerapi
                            )
                        }
                        viewModel.updateJenisTerapi(jenisTerapi)
                        navigateBack()

                    }
                },





            )
        }
    }
}
