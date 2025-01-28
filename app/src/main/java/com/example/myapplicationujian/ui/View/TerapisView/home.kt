package com.example.myapplicationujian.ui.View.TerapisView

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.Model.Terapis
import com.example.myapplicationujian.R
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.CostumeWidget.MenuButton
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.View.PasienView.DestinasiHome
import com.example.myapplicationujian.ui.View.PasienView.OnError
import com.example.myapplicationujian.ui.View.PasienView.OnLoading
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.HomeUiState
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.HomeViewModelTerapis


object DestinasiHomeTerapis : DestinasiNavigasi {
    override val route = "hometrps"
    override val titleRes = "Home Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTerapis(
    navigateToTerapisEntry: () -> Unit,
    navigateToPasien: () -> Unit,
    navigateToTerapis: () -> Unit,
    navigateToJenisTerapi: () -> Unit,
    navigateToSesiTerapi: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToUpdateTerapis: (String) -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModelTerapis = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CostumeTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTerapis()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTerapis,
                shape = MaterialTheme.shapes.medium,

                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Terapis")
            }
            FloatingActionButton(
                onClick = navigateToTerapisEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFFD6ED17), // Warna FAB

                modifier = Modifier.padding(18.dp)

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")

            }
        },
        bottomBar = {
            MenuButton(
                onPasienClick = navigateToPasien,
                onTerapisClick = navigateToTerapis,
                onJenisTerapiClick = navigateToJenisTerapi,
                onSesiTerapiClick = navigateToSesiTerapi,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Gambar background blur
            Image(
                painter = painterResource(id = R.drawable.log), // Ganti dengan gambar yang sesuai
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        // Blur Effect
                        shadowElevation = 10f
                    }
            )
            TerapisStatus(
                homeUiState = viewModel.terapisUIState,
                retryAction = { viewModel.getTerapis() },
                modifier = Modifier.padding(innerPadding),
                onDetailClick = onDetailClick,
                onDeleteClick = { terapis ->
                    viewModel.deleteTerapis(terapis.id_terapis)
                    viewModel.getTerapis()
                }
            )
        }
    }
}

@Composable
fun TerapisStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Terapis) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if (homeUiState.terapis.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data terapis")
                }
            } else {
                TerapisLayout(
                    terapis = homeUiState.terapis,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_terapis) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
        else -> {}
    }
}

@Composable
fun TerapisLayout(
    terapis: List<Terapis>,
    modifier: Modifier = Modifier,
    onDetailClick: (Terapis) -> Unit,
    onDeleteClick: (Terapis) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(terapis) { terapis ->
            TerapisCard(
                terapis = terapis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(terapis) },
                onDeleteClick = { onDeleteClick(terapis) }
            )
        }
    }
}

@Composable
fun TerapisCard(
    terapis: Terapis,
    modifier: Modifier = Modifier,
    onDeleteClick: (Terapis) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .graphicsLayer {
                shadowElevation = 16f
                shape = RoundedCornerShape(16.dp)
                clip = true
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = terapis.nama_terapis,
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(terapis) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Terapis",
                        tint = Color.Red // Memberikan warna merah pada ikon hapus
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.log), // Ikon spesialisasi
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Spesialisasi: ${terapis.spesialisasi}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.log), // Ikon nomor izin praktik
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Nomor Izin Praktik: ${terapis.nomor_izin_praktik}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}
