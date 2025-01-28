package com.example.myapplicationujian.ui.View.JenisTerapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.InsertJenisTerapiUiEvent
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.InsertJenisTerapiUiState
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.InsertJenisTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.DetailTerapisViewModel
import kotlinx.coroutines.launch

object DestinasiEntryJenisTerapi : DestinasiNavigasi {
    override val route = "item_entry_jenis_terapi"
    override val titleRes = "Entry Jenis Terapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryJenisTerapiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryJenisTerapi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyJenisTerapi(
            insertUiState = viewModel.uiState,
            onJenisTerapiValueChange = viewModel::updateInsertJenisTerapiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertJenisTerapi()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyJenisTerapi(
    insertUiState: InsertJenisTerapiUiState,
    onJenisTerapiValueChange: (InsertJenisTerapiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputJenisTerapi(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onJenisTerapiValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD6ED17), // Warna latar belakang
                contentColor = Color.Black // Warna teks
            )
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputJenisTerapi(
    insertUiEvent: InsertJenisTerapiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertJenisTerapiUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaJenisTerapi,
            onValueChange = { onValueChange(insertUiEvent.copy(namaJenisTerapi = it)) },
            label = { Text("Nama Jenis Terapi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,

            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // Warna latar belakang
            ),
            shape = RoundedCornerShape(50.dp),

            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiTerapi,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiTerapi = it)) },
            label = { Text("Deskripsi Terapi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            shape = RoundedCornerShape(50.dp),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // Warna latar belakang
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )


        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }


    }
}