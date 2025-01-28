package com.example.myapplicationujian.ui.View.PasienView

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.Model.Pasien
import com.example.myapplicationujian.R
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.pasienViewModel.DetailPasienViewModel
import com.example.myapplicationujian.ui.ViewModel.pasienViewModel.DetailUiState
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail pasien"
    const val idpasien = "idpasien"
    val routeWithArg = "$route/{$idpasien}"
    override val titleRes = "Detail Pasien"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPasienView(
    idPasien: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailPasienViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailPasien() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idPasien) }, // Navigasi ke halaman edit
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFFD6ED17), // Warna FAB
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pasien"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image with Blur Effect
            Image(
                painter = painterResource(id = R.drawable.log), // Gambar latar belakang
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        // Efek blur dan bayangan pada gambar latar belakang
                        this.shadowElevation = 8f
                    }
            )

            // Konten
            when (val state = detailUiState) {
                is DetailUiState.Loading -> {
                    OnLoading(modifier = Modifier.fillMaxSize())
                }
                is DetailUiState.Success -> {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        ItemDetailPasien(pasien = state.pasien)
                    }
                }
                is DetailUiState.Error -> {
                    OnError(
                        retryAction = { viewModel.getDetailPasien() },
                        modifier = modifier.fillMaxSize()
                    )
                }
                else -> {
                    Text("Unexpected state")
                }
            }
        }
    }
}

@Composable
fun ItemDetailPasien(pasien: Pasien) {
    val formattedTanggalLahir = remember(pasien.tanggal_lahir) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(pasien.tanggal_lahir)
        parsedDate?.let { format.format(it) } ?: pasien.tanggal_lahir // Handle null case
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, // Menggunakan latar belakang putih pada kartu
            contentColor = MaterialTheme.colorScheme.onSurface // Warna teks yang kontras dengan latar belakang
        ),
        elevation = CardDefaults.cardElevation(12.dp), // Efek bayangan lebih halus
        shape = MaterialTheme.shapes.large // Sudut membulat
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPasien(judul = "ID Pasien", isinya = pasien.id_pasien.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Nama", isinya = pasien.nama_pasien)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Alamat", isinya = pasien.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Nomor Telepon", isinya = pasien.nomor_telepon)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Tanggal Lahir", isinya = formattedTanggalLahir)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Riwayat Medikal", isinya = pasien.riwayat_medikal)
        }
    }
}

@Composable
fun ComponentDetailPasien(judul: String, isinya: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 18.sp, // Ukuran font lebih kecil untuk judul
            fontWeight = FontWeight.Medium, // Ketebalan sedang untuk judul
            color = MaterialTheme.colorScheme.secondary // Menggunakan warna sekunder dari tema
        )

        Text(
            text = isinya,
            fontSize = 20.sp, // Ukuran font lebih besar untuk isi
            fontWeight = FontWeight.Bold, // Ketebalan teks lebih tebal
            color = MaterialTheme.colorScheme.onSurface // Menggunakan warna teks yang kontras dengan latar belakang
        )
    }
}
