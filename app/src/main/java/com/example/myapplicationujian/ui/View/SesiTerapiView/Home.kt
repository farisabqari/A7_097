package com.example.myapplicationujian.ui.View.SesiTerapiView

import com.example.myapplicationujian.Model.SesiTerapi
import com.example.myapplicationujian.ui.CostumeWidget.CostumeTopAppBar
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi
import com.example.myapplicationujian.ui.ViewModel.PenyediaViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.HomeSesiTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.HomeStUiState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.myapplicationujian.R
import com.example.myapplicationujian.ui.CostumeWidget.MenuButton

object DestinasiHomeSesi: DestinasiNavigasi {
    override val route = "HomSesi"
    override val titleRes = "HoMe Sesi"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenSesi(
    navigateToSesiEntry: () -> Unit,
    navigateToPasien: () -> Unit,
    navigateToTerapis: () -> Unit,
    navigateToJenisTerapi: () -> Unit,
    navigateToSesiTerapi: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToUpdatSesiTerapi: (String) -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeSesiTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeSesi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getsesi()
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToSesiEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFFD6ED17),

                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah sesi ")
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
    ) {innerPadding ->
        HomeStatusMtg(
            homeStUiState = viewModel.stUiState,
            retryAction = { viewModel.getsesi() }, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletSesi(it.id_sesi)
                viewModel.getsesi()
            }
        )

    }
}

@Composable
fun HomeStatusMtg(
    homeStUiState: HomeStUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (SesiTerapi) -> Unit = {},
    onDetailClick: (Int) -> Unit
){
    when (homeStUiState){
        is HomeStUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())


        is HomeStUiState.Success ->
            if (homeStUiState.SesiTerapi.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Kandang")
                }
            }else{
               SesiLayout(
                   SesiTerapi = homeStUiState.SesiTerapi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_sesi)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeStUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.eror), contentDescription = "")
        Text(text = stringResource(id = R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun SesiLayout(
    SesiTerapi: List<SesiTerapi>,
    modifier: Modifier = Modifier,
    onDetailClick: (SesiTerapi) -> Unit,
    onDeleteClick: (SesiTerapi) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(SesiTerapi){ SesiTerapi ->
            SesiCard(
                SesiTerapi = SesiTerapi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(SesiTerapi) },
                onDeleteClick = {
                    onDeleteClick(SesiTerapi)
                }
            )
        }
    }
}

@Composable
fun SesiCard(
    SesiTerapi: SesiTerapi,
    modifier: Modifier = Modifier,
    onDeleteClick: (SesiTerapi) -> Unit = {}
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
        colors = CardDefaults.cardColors(containerColor = Color.White) // Warna latar belakang modern
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
                    text = SesiTerapi.tanggal_sesi ?: "Tanggal Tidak Diketahui",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(SesiTerapi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Sesi",
                        tint = Color.Red // Ikon warna merah untuk tindakan hapus
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.log), // Ikon untuk catatan sesi
                    contentDescription = "Catatan Sesi",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = SesiTerapi.catatan_sesi,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}
