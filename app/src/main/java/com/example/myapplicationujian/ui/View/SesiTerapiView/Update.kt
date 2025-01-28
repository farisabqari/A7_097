package com.example.myapplicationujian.ui.View.SesiTerapiView

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.UpdateSesiTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.UpdateTerapisViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateSesi: DestinasiNavigasi {
    override val route = "updateSesi"
    override val titleRes = "Update Sesi"
    const val idSesi = "idSesi"
    val routesWithArg = "$route/{$idSesi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenSesi(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateSesiTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateSesi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        }
    ){padding ->
        EntryBodySesi(
            modifier = Modifier.padding(padding),
            insertSesiUiState = viewModel.stupdateUiState,
            onSesiValueChange = viewModel::updateInsertstState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSt()
                    delay(600)
                    withContext(Dispatchers.Main){

                    }
                    navigateBack() // Navigate back after saving

                }
            }
        )
    }
}