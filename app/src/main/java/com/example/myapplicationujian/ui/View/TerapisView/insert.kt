package com.example.myapplicationujian.ui.View.TerapisView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.R
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.InsertTerapisUiEvent
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.InsertTerapisUiState
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.InsertTerapisViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTerapis : DestinasiNavigasi {
    override val route = "item_entry_terapis"
    override val titleRes = "Entry Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTerapisScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(

        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTerapis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
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
            EntryBodyTerapis(
                insertUiState = viewModel.uiState,
                onTerapisValueChange = viewModel::updateInsertTerapisState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertTerapis()
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
}

@Composable
fun EntryBodyTerapis(
    insertUiState: InsertTerapisUiState,
    onTerapisValueChange: (InsertTerapisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTerapis(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onTerapisValueChange,
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
fun FormInputTerapis(
    insertUiEvent: InsertTerapisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTerapisUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaTerapis,
            onValueChange = { onValueChange(insertUiEvent.copy(namaTerapis = it)) },
            label = { Text("Nama Terapis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            shape = RoundedCornerShape(50.dp),

            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )

        OutlinedTextField(
            value = insertUiEvent.spesialisasi,
            onValueChange = { onValueChange(insertUiEvent.copy(spesialisasi = it)) },
            label = { Text("Spesialisasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            shape = RoundedCornerShape(50.dp),

            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // Warna latar belakang
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )

        OutlinedTextField(
            value = insertUiEvent.nomorIzinPraktik,
            onValueChange = { onValueChange(insertUiEvent.copy(nomorIzinPraktik = it)) },
            label = { Text("Nomor Izin Praktik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            shape = RoundedCornerShape(50.dp),

            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // Warna latar belakang
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Star,
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
