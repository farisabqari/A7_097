package com.example.myapplicationujian.ui.View.JenisTerapi

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationujian.Model.JenisTerapi
import com.example.myapplicationujian.R
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.CostumeWidget.MenuButton
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.View.PasienView.OnError
import com.example.myapplicationujian.ui.View.PasienView.OnLoading
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.HomeJTUiState
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.HomeJenisTerapisViewModel
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.HomeViewModelTerapis


object DestinasiHomeJenisTerapi : DestinasiNavigasi {
    override val route = "homeJenis"
    override val titleRes = "Home Jenis Terapi"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenJenisTerapi(
    navigateToJenisTerapiEntry: () -> Unit,
    navigateToPasien: () -> Unit,
    navigateToTerapis: () -> Unit,
    navigateToJenisTerapi: () -> Unit,
    navigateToSesiTerapi: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToUpdateJenisTerapis: (Int) -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeJenisTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Jenis Terapi",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getJenisTerapi()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToJenisTerapiEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFFD6ED17), // Warna FAB

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Jenis Terapi")
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
            JenisTerapiStatus(
                uiState = viewModel.jterapisUIState,
                retryAction = { viewModel.getJenisTerapi() },
                modifier = Modifier.padding(innerPadding),
                onDetailClick = onDetailClick,
                onDeleteClick = { idJenisTerapis ->
                    viewModel.deleteJenisTerapi(idJenisTerapis)
                    viewModel.getJenisTerapi() // Refresh data
                }
            )
        }
    }
}

@Composable
fun JenisTerapiStatus(
    uiState: HomeJTUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Int) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    when (uiState) {
        is HomeJTUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeJTUiState.Success -> {
            if (uiState.jenisTerapi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data jenis terapi")
                }
            } else {
                JenisTerapiList(
                    jenisTerapiList = uiState.jenisTerapi,
                    onDeleteClick = onDeleteClick,
                    onDetailClick = onDetailClick
                )
            }
        }
        is HomeJTUiState.Error -> OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun JenisTerapiList(
    jenisTerapiList: List<JenisTerapi>,
    onDeleteClick: (Int) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jenisTerapiList) { jenisTerapi ->
            JenisTerapiCard(
                jenisTerapi = jenisTerapi,
                onDeleteClick = { onDeleteClick(jenisTerapi.id_jenis_terapi) },
                onDetailClick = { onDetailClick(jenisTerapi.id_jenis_terapi) }
            )
        }
    }
}

@Composable
fun JenisTerapiCard(
    jenisTerapi: JenisTerapi,
    onDeleteClick: () -> Unit,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .graphicsLayer {
                shadowElevation = 10f
                shape = RoundedCornerShape(12.dp)
                clip = true
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onDetailClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = jenisTerapi.nama_jenis_terapi,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = jenisTerapi.deskripsi_terapi,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus",
                    tint = Color.Red
                )
            }
        }
    }
}
