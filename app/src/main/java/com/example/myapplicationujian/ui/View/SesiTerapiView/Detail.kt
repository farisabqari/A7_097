package com.example.myapplicationujian.ui.View.SesiTerapiView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.Model.SesiTerapi
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.DetailSesiTerapiViewModel

object DestinasiDetailSesi : DestinasiNavigasi {
    override val route = "detail_Sesi"
    override val titleRes = "Detail Sesi"
    const val idSesi = "idSesi"
    val routeWithArgs = "$route/{$idSesi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenSesi(
    id_Sesi: Int,
    onEditClick: (String) -> Unit = { },
    onBackClick: () -> Unit = { },
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailSesiTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val SesiTerapi = viewModel.stUiState.detailSesiUiEvent

    LaunchedEffect(id_Sesi) {
        viewModel.fetchDetailSesi(id_Sesi)
    }

    val isLoading = viewModel.stUiState.isLoading
    val isError = viewModel.stUiState.isError
    val errorMessage = viewModel.stUiState.errorMessage

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetailSesi.titleRes) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(SesiTerapi.idSesi.toString()) },
                containerColor = Color(0xFFD6ED17), // Warna FAB

            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Data")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (viewModel.stUiState.isUiEventNotEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Modern card with white background
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White, // Latar belakang putih
                                contentColor = MaterialTheme.colorScheme.onSurface // Warna teks
                            ),
                            elevation = CardDefaults.cardElevation(8.dp), // Elevasi untuk bayangan lembut
                            shape = MaterialTheme.shapes.large // Sudut kartu membulat
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                DetailRowSesi(label = "Id Sesi", value = SesiTerapi.idSesi.toString())
                                DetailRowSesi(label = "Id Pasien", value = SesiTerapi.idPasien.toString())
                                DetailRowSesi(label = "Id Terapis", value = SesiTerapi.idTerapis.toString())
                                DetailRowSesi(label = "Id Jenis Terapi", value = SesiTerapi.idJenisTerapi.toString())
                                DetailRowSesi(label = "Tanggal Sesi", value = SesiTerapi.tanggalSesi)
                                DetailRowSesi(label = "Catatan Sesi", value = SesiTerapi.catatanSesi)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DetailRowSesi(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary, // Warna utama untuk label
            modifier = Modifier.weight(1f)
        )
        Text(
            text = ": $value",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface, // Warna teks isi
            modifier = Modifier.weight(2f)
        )
    }
}
