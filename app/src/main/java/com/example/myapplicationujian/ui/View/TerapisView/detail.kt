package com.example.myapplicationujian.ui.View.TerapisView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.Model.Terapis
import com.example.myapplicationujian.R
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.View.PasienView.OnError
import com.example.myapplicationujian.ui.View.PasienView.OnLoading
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.DetailTerapisUiState
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.DetailTerapisViewModel


object DestinasiDetailTerapis : DestinasiNavigasi {
    override val route = "detail_terapis"
    const val idTerapis = "id_terapis"
    val routeWithArg = "$route/{$idTerapis}"
    override val titleRes = "Detail Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTerapisView(
    idTerapis: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTerapis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailTerapis() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(idTerapis) // Menggunakan idTerapis untuk navigasi ke update
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFFD6ED17), // Warna FAB
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Terapis"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image with Blur Effect
            Image(
                painter = painterResource(id = R.drawable.log), // Background image
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        // Blur effect and shadow
                        this.shadowElevation = 8f
                    }
            )

            BodyDetailTerapis(
                modifier = Modifier.padding(innerPadding),
                detailUiState = detailUiState,
                retryAction = { viewModel.getDetailTerapis() }
            )
        }
    }
}

@Composable
fun BodyDetailTerapis(
    modifier: Modifier = Modifier,
    detailUiState: DetailTerapisUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailTerapisUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is DetailTerapisUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                ItemDetailTerapis(terapis = detailUiState.terapis)
            }
        }

        is DetailTerapisUiState.Error -> {
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }

        else -> {
            Text("Unexpected")
        }
    }
}

@Composable
fun ItemDetailTerapis(
    terapis: Terapis
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, // Latar belakang putih
            contentColor = MaterialTheme.colorScheme.onSurface // Warna teks kontras
        ),
        elevation = CardDefaults.cardElevation(12.dp), // Elevasi lebih tinggi untuk efek bayangan halus
        shape = MaterialTheme.shapes.large // Sudut membulat
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailTerapis(judul = "ID Terapis", isinya = terapis.id_terapis.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTerapis(judul = "Nama Terapis", isinya = terapis.nama_terapis)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTerapis(judul = "Spesialisasi", isinya = terapis.spesialisasi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTerapis(judul = "Nomor Izin Praktik", isinya = terapis.nomor_izin_praktik)
        }
    }
}

@Composable
fun ComponentDetailTerapis(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary // Warna teks lebih lembut untuk judul
        )

        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface // Warna teks yang kontras
        )
    }
}
