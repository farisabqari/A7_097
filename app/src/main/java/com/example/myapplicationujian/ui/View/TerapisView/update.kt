package com.example.myapplicationujian.ui.View.TerapisView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.R
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.UpdateTerapisViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.toTerapis
import kotlinx.coroutines.launch

object DestinasiUpdateTerapis : DestinasiNavigasi {
    override val route = "update_terapis"
    const val idTerapis = "id_terapis"
    val routeWithArg = "$route/{$idTerapis}"
    override val titleRes = "Update Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTerapisView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTerapis.titleRes,
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // Terapis entry form body
                EntryBodyTerapis(
                    insertUiState = uiState,
                    onTerapisValueChange = { updateValue ->
                        viewModel.updateTerapisState(updateValue)
                    },
                    onSaveClick = {
                        uiState.insertUiEvent?.let { insertUiEvent ->
                            coroutineScope.launch {
                                viewModel.updateTerapis(
                                    idTerapis = viewModel.idTerapis,
                                    terapis = insertUiEvent.toTerapis()
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