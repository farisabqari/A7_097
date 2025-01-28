package com.example.myapplicationujian.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplicationujian.TerapiApplications
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.DetailJenisTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.HomeJenisTerapisViewModel
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.InsertJenisTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.JenisTerapi.UpdateJenisTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.DetailSesiTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.HomeSesiTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.InsertSesiViewModel
import com.example.myapplicationujian.ui.ViewModel.SesiTerapiViewModel.UpdateSesiTerapiViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.DetailTerapisViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.HomeViewModelTerapis
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.InsertTerapisViewModel
import com.example.myapplicationujian.ui.ViewModel.TerapiViewModel.UpdateTerapisViewModel
import com.example.myapplicationujian.ui.ViewModel.pasienViewModel.DetailPasienViewModel
import com.example.myapplicationujian.ui.ViewModel.pasienViewModel.HomeViewModelPasien
import com.example.myapplicationujian.ui.ViewModel.pasienViewModel.InsertPasienViewModel
import com.example.myapplicationujian.ui.ViewModel.pasienViewModel.UpdatePasienViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Pasien
        initializer { HomeViewModelPasien(aplikasiTerapi().container.pasienRepository) }
        initializer { InsertPasienViewModel(aplikasiTerapi().container.pasienRepository) }
        initializer { DetailPasienViewModel(createSavedStateHandle(), aplikasiTerapi().container.pasienRepository) }
        initializer { UpdatePasienViewModel(createSavedStateHandle(), aplikasiTerapi().container.pasienRepository) }

        // Terapis
        initializer { HomeViewModelTerapis(aplikasiTerapi().container.terapisRepository) }
        initializer { InsertTerapisViewModel(aplikasiTerapi().container.terapisRepository) }
        initializer { DetailTerapisViewModel(createSavedStateHandle(), aplikasiTerapi().container.terapisRepository) }
        initializer { UpdateTerapisViewModel(createSavedStateHandle(), aplikasiTerapi().container.terapisRepository) }

        // Jenis Terapi
        initializer { HomeJenisTerapisViewModel(aplikasiTerapi().container.jenisTerapiRepository) }
        initializer { InsertJenisTerapiViewModel(aplikasiTerapi().container.jenisTerapiRepository) }
        initializer { DetailJenisTerapiViewModel(createSavedStateHandle(), aplikasiTerapi().container.jenisTerapiRepository) }
        initializer { UpdateJenisTerapiViewModel(createSavedStateHandle(), aplikasiTerapi().container.jenisTerapiRepository) }

        // Sesi Terapi
        initializer {
            HomeSesiTerapiViewModel(aplikasiTerapi().container.sesiTerapiRepository)
        }
        initializer {
            InsertSesiViewModel(
                sesiRepository = aplikasiTerapi().container.sesiTerapiRepository,
                pasienRepository = aplikasiTerapi().container.pasienRepository,
                terapisRepository = aplikasiTerapi().container.terapisRepository,
                jenisTerapiRepository = aplikasiTerapi().container.jenisTerapiRepository
            )
        }
        initializer { UpdateSesiTerapiViewModel(createSavedStateHandle(), aplikasiTerapi().container.sesiTerapiRepository) }
        initializer { DetailSesiTerapiViewModel(createSavedStateHandle(), aplikasiTerapi().container.sesiTerapiRepository) }




    }
}

fun CreationExtras.aplikasiTerapi(): TerapiApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TerapiApplications)
