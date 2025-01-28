package com.example.myapplicationujian.ui.View.JenisTerapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.View.PasienView.OnError
import com.example.myapplicationujian.ui.View.PasienView.OnLoading
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.DetailJenisTerapiUiState
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.DetailJenisTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel


object DestinasiDetailJenisTerapi : DestinasiNavigasi {
    override val route = "detail_jenis_terapi"
    const val idJenisTerapi = "id_jenis_terapi"
    val routeWithArg = "$route/{$idJenisTerapi}"
    override val titleRes = "Detail Jenis Terapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJenisTerapiView(
    idJenisTerapi: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailJenisTerapi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailJenisTerapi() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(idJenisTerapi)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFFD6ED17), // Warna FAB
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jenis Terapi"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailJenisTerapi(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            retryAction = { viewModel.getDetailJenisTerapi() }
        )
    }
}

@Composable
fun BodyDetailJenisTerapi(
    modifier: Modifier = Modifier,
    detailUiState: DetailJenisTerapiUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailJenisTerapiUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is DetailJenisTerapiUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                ItemDetailJenisTerapi(jenisTerapi = detailUiState.jenisTerapi)
            }
        }

        is DetailJenisTerapiUiState.Error -> {
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
fun ItemDetailJenisTerapi(
    jenisTerapi: JenisTerapi
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Latar belakang putih
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Elevasi untuk bayangan lembut
        shape = MaterialTheme.shapes.large // Sudut membulat
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ComponentDetailJenisTerapi(
                judul = "ID Jenis Terapi",
                isinya = jenisTerapi.id_jenis_terapi.toString(),
                icon = Icons.Default.Edit
            )
            ComponentDetailJenisTerapi(
                judul = "Nama Jenis Terapi",
                isinya = jenisTerapi.nama_jenis_terapi,
                icon = Icons.Default.Edit
            )
            ComponentDetailJenisTerapi(
                judul = "Deskripsi Terapi",
                isinya = jenisTerapi.deskripsi_terapi,
                icon = Icons.Default.Edit
            )
        }
    }
}

@Composable
fun ComponentDetailJenisTerapi(
    judul: String,
    isinya: String,
    icon: ImageVector? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = judul,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface // Warna teks utama
            )
            Text(
                text = isinya,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Warna teks sekunder
            )
        }
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
