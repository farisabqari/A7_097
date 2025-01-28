package com.example.myapplicationujian.ui.Navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplicationujian.ui.View.DestinasiSplashView
import com.example.myapplicationujian.ui.View.JenisTerapi.DestinasiDetailJenisTerapi
import com.example.myapplicationujian.ui.View.JenisTerapi.DestinasiEntryJenisTerapi
import com.example.myapplicationujian.ui.View.JenisTerapi.DestinasiHomeJenisTerapi
import com.example.myapplicationujian.ui.View.JenisTerapi.DestinasiUpdateJenisTerapi
import com.example.myapplicationujian.ui.View.JenisTerapi.DetailJenisTerapiView
import com.example.myapplicationujian.ui.View.JenisTerapi.EntryJenisTerapiScreen
import com.example.myapplicationujian.ui.View.JenisTerapi.HomeScreenJenisTerapi
import com.example.myapplicationujian.ui.View.JenisTerapi.UpdateJenisTerapiScreen
import com.example.myapplicationujian.ui.View.PasienView.*
import com.example.myapplicationujian.ui.View.SesiTerapiView.DestinasiDetailSesi
import com.example.myapplicationujian.ui.View.SesiTerapiView.DestinasiHomeSesi
import com.example.myapplicationujian.ui.View.SesiTerapiView.DestinasiInsertSesi
import com.example.myapplicationujian.ui.View.SesiTerapiView.DestinasiUpdateSesi
import com.example.myapplicationujian.ui.View.SesiTerapiView.DetailScreenSesi
import com.example.myapplicationujian.ui.View.SesiTerapiView.EntrySesiScreen
import com.example.myapplicationujian.ui.View.SesiTerapiView.HomeScreenSesi
import com.example.myapplicationujian.ui.View.SesiTerapiView.UpdateScreenSesi
import com.example.myapplicationujian.ui.View.SplashView
import com.example.myapplicationujian.ui.View.TerapisView.*

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplashView.route,
        modifier = modifier
    ) {
        composable(route = DestinasiSplashView.route) {
            SplashView(
                navigatetoSpalash = { navController.navigate(DestinasiHome.route)

            }
            )
        }

        // Home Pasien
        composable(DestinasiHome.route) {
            HomeScreenPasien(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPasien.route) },
                onDetailClick = { idPasien ->
                    navController.navigate("${DestinasiDetail.route}/$idPasien")
                },
                navigateToPasien = { navController.navigate(DestinasiHome.route) },
                navigateToTerapis = { navController.navigate(DestinasiHomeTerapis.route) },
                navigateToJenisTerapi = { navController.navigate(DestinasiHomeJenisTerapi.route) },
                navigateToSesiTerapi = { navController.navigate(DestinasiHomeSesi.route)},
                navigateToUpdatePasien = { idPasien ->
                    navController.navigate("${DestinasiUpdate.route}/$idPasien")
                }
            )
        }

        // Entry Pasien
        composable(DestinasiEntryPasien.route) {
            EntryPasienScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Pasien
        composable(
            DestinasiDetail.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetail.idpasien) { type = NavType.IntType })
        ) { backStackEntry ->
            val idPasien = backStackEntry.arguments?.getInt(DestinasiDetail.idpasien) ?: -1
            DetailPasienView(
                idPasien = idPasien,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idPasien ->
                    navController.navigate("${DestinasiUpdate.route}/$idPasien")
                }
            )
        }

        // Update Pasien
        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdate.idpasien) { type = NavType.IntType })
        ) { backStackEntry ->
            val idPasien = backStackEntry.arguments?.getInt(DestinasiUpdate.idpasien)
            idPasien?.let {
                UpdatePasienView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Home Terapis
        composable(DestinasiHomeTerapis.route) {
            HomeScreenTerapis(
                navigateToTerapisEntry = { navController.navigate(DestinasiEntryTerapis.route) },
                onDetailClick = { idTerapis ->
                    navController.navigate("${DestinasiDetailTerapis.route}/$idTerapis")
                },
                navigateToPasien = { navController.navigate(DestinasiHome.route) },
                navigateToTerapis = { navController.navigate(DestinasiHomeTerapis.route) },
                navigateToJenisTerapi = {navController.navigate(DestinasiHomeJenisTerapi.route)  },
                navigateToSesiTerapi = { navController.navigate(DestinasiHomeSesi.route)},
                navigateToUpdateTerapis = { idTerapis ->
                    navController.navigate("${DestinasiUpdateTerapis.route}/$idTerapis")
                }
            )
        }

        // Entry Terapis
        composable(DestinasiEntryTerapis.route) {
            EntryTerapisScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Terapis
        composable(
            DestinasiDetailTerapis.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailTerapis.idTerapis) { type = NavType.IntType })
        ) { backStackEntry ->
            val idTerapis = backStackEntry.arguments?.getInt(DestinasiDetailTerapis.idTerapis) ?: -1
            DetailTerapisView(
                idTerapis = idTerapis,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idTerapis ->
                    navController.navigate("${DestinasiUpdateTerapis.route}/$idTerapis")
                }
            )
        }

        // Update Terapis
        composable(
            DestinasiUpdateTerapis.routeWithArg,
            arguments = listOf(navArgument(DestinasiUpdateTerapis.idTerapis) { type = NavType.IntType })
        ) { backStackEntry ->
            val idTerapis = backStackEntry.arguments?.getInt(DestinasiUpdateTerapis.idTerapis)
            idTerapis?.let {
                UpdateTerapisView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
        composable(DestinasiHomeJenisTerapi.route) {
            HomeScreenJenisTerapi(
                navigateToJenisTerapiEntry = { navController.navigate(DestinasiEntryJenisTerapi.route) },
                onDetailClick = { idJenisTerapi ->
                    navController.navigate("${DestinasiDetailJenisTerapi.route}/$idJenisTerapi")
                },
                navigateToPasien = { navController.navigate(DestinasiHome.route) },
                navigateToTerapis = { navController.navigate(DestinasiHomeTerapis.route) },
                navigateToJenisTerapi = { navController.navigate(DestinasiHomeJenisTerapi.route) },
                navigateToSesiTerapi = { navController.navigate(DestinasiHomeSesi.route)},
                navigateToUpdateJenisTerapis = { idJenisTerapi ->
                    navController.navigate("${DestinasiUpdateJenisTerapi.route}/$idJenisTerapi")
                }
            )
        }
        composable(DestinasiEntryJenisTerapi.route) {
            EntryJenisTerapiScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            DestinasiDetailJenisTerapi.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailJenisTerapi.idJenisTerapi) { type = NavType.IntType })
        ) { backStackEntry ->
            val idJenisTerapi = backStackEntry.arguments?.getInt(DestinasiDetailJenisTerapi.idJenisTerapi) ?: -1
            DetailJenisTerapiView(
                idJenisTerapi = idJenisTerapi,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idJenisTerapi ->
                    navController.navigate("${DestinasiUpdateJenisTerapi.route}/$idJenisTerapi")
                }
            )
        }
        composable(
            DestinasiUpdateJenisTerapi.routeWithArg,
            arguments = listOf(navArgument(DestinasiUpdateJenisTerapi.idJenisTerapi) { type = NavType.IntType })
        ) { backStackEntry ->
            val idJenisTerapi = backStackEntry.arguments?.getInt(DestinasiUpdateJenisTerapi.idJenisTerapi)
            idJenisTerapi?.let {
                UpdateJenisTerapiScreen(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
        //Sesi Terapi
        composable(DestinasiHomeSesi.route) {
            HomeScreenSesi(
                navigateToSesiEntry = { navController.navigate(DestinasiInsertSesi.route) },
                onDetailClick = { idSesi ->
                    navController.navigate("${DestinasiDetailSesi.route}/$idSesi")
                },
                navigateToPasien = { navController.navigate(DestinasiHome.route) },
                navigateToTerapis = { navController.navigate(DestinasiHomeTerapis.route) },
                navigateToJenisTerapi = { navController.navigate(DestinasiHomeJenisTerapi.route) },
                navigateToSesiTerapi = { navController.navigate(DestinasiHomeSesi.route)},
                navigateToUpdatSesiTerapi = { idSesi ->
                    navController.navigate("${DestinasiUpdateSesi.route}/$idSesi")
                }
            )
        }
        composable(DestinasiInsertSesi.route) {
            EntrySesiScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            DestinasiDetailSesi.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateSesi.idSesi) { type = NavType.IntType })
        ) { backStackEntry ->
            val idSesi = backStackEntry.arguments?.getInt(DestinasiUpdateSesi.idSesi) ?: -1
            DetailScreenSesi(
                id_Sesi = idSesi,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idSesi ->
                    navController.navigate("${DestinasiUpdateSesi.route}/$idSesi")
                }
            )
        }
        composable(
            DestinasiUpdateSesi.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateSesi.idSesi) { type = NavType.IntType })
        ) { backStackEntry ->
            val idSesi = backStackEntry.arguments?.getInt(DestinasiUpdateSesi.idSesi)
            idSesi?.let {
                UpdateScreenSesi(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }




    }
}
