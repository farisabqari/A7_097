package com.example.myapplicationujian.ui.View.SesiTerapiView

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.InsertSesiUiEvent
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.InsertSesiUiState
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.InsertSesiViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

object DestinasiInsertSesi: DestinasiNavigasi {
    override val route= "EntrySesi"
    override val titleRes = "Entry Sesi"

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySesiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertSesiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertSesi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {innerPadding ->
        EntryBodySesi(
            insertSesiUiState = viewModel.stuiState,
            onSesiValueChange = viewModel::updateInsertState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertSesiTerapi()
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
fun EntryBodySesi(
    insertSesiUiState: InsertSesiUiState,
    onSesiValueChange: (InsertSesiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputSesi(
            insertSesiUiEvent = insertSesiUiState.insertstEvent,
            onValueChange = onSesiValueChange,
            modifier = Modifier.fillMaxWidth())
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
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
fun FormInputSesi(
    insertSesiUiEvent: InsertSesiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertSesiUiEvent) -> Unit = {},
    viewModel: InsertSesiViewModel = viewModel(factory = PenyediaViewModel.Factory),
    enabled: Boolean = true
) {
    val pasienOptions = viewModel.stuiState.pasienOptions
    val terapisOptions = viewModel.stuiState.terapisOptions
    val jenisTerapiOptions = viewModel.stuiState.jenisTerapiOptions

    val context = LocalContext.current

    // DatePickerDialog initialization
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                // Update only the tanggalLahir field, keeping other fields the same
                onValueChange(insertSesiUiEvent.copy(tanggalSesi = formattedDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DropDownAll(
            title = "Pilih Pasien",
            options = pasienOptions.map { it.label },
            selectedOption = pasienOptions.find { it.id_Pasien == insertSesiUiEvent.idPasien }?.label.orEmpty(),
            onOptionSelected = { label ->
                val selected = pasienOptions.find { it.label == label }
                onValueChange(insertSesiUiEvent.copy(idPasien = selected?.id_Pasien ?: 0))
            }
        )

        DropDownAll(
            title = "Pilih Terapis",
            options = terapisOptions.map { it.label },
            selectedOption = terapisOptions.find { it.id_Terapis == insertSesiUiEvent.idTerapis }?.label.orEmpty(),
            onOptionSelected = { label ->
                val selected = terapisOptions.find { it.label == label }
                onValueChange(insertSesiUiEvent.copy(idTerapis = selected?.id_Terapis ?: 0))
            },

        )

        DropDownAll(
            title = "Pilih Jenis Terapi",
            options = jenisTerapiOptions.map { it.label },
            selectedOption = jenisTerapiOptions.find { it.id_JenisTerapi == insertSesiUiEvent.idJenisTerapi }?.label.orEmpty(),
            onOptionSelected = { label ->
                val selected = jenisTerapiOptions.find { it.label == label }
                onValueChange(insertSesiUiEvent.copy(idJenisTerapi = selected?.id_JenisTerapi ?: 0))


            },


        )

        OutlinedTextField(
            value = insertSesiUiEvent.tanggalSesi,
            onValueChange = { /* Disable direct input */ },
            label = { Text("Tanggal Lahir") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            enabled = false,
            shape = RoundedCornerShape(50.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // Warna latar belakang
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )

        OutlinedTextField(
            value = insertSesiUiEvent.catatanSesi,
            onValueChange = { onValueChange(insertSesiUiEvent.copy(catatanSesi = it)) },
            label = { Text("Masukkan Catatan Sesi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
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

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}



@Composable
fun DropDownAll(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = title) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        // Ensure dropdown opens and closes properly
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)  // Update selected value
                        expanded = false // Close dropdown
                    }
                )
            }
        }
        // Handle error display if needed
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
