package am.android.parking.navigation

import am.android.parking.analytics.presentation.AnalyticsScreen
import am.android.parking.diagrams.presentation.DiagramsScreen
import am.android.parking.settings.presentation.SettingsScreen
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startDestination: Destination = Destination.Analytics,
    onLanguageChange: (String) -> Unit = {},
) {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    systemUiController.systemBarsDarkContentEnabled = true

    /*
        val currentDestination by navController.currentBackStackEntryAsState()

        LaunchedEffect(currentDestination) {
            when (currentDestination?.destination?.route) {
                Destination.Settings::class.qualifiedName -> {
                    systemUiController.systemBarsDarkContentEnabled = false
                }

                else -> {
                    systemUiController.systemBarsDarkContentEnabled = true
                }
            }
        }
    */

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Destination.Settings>(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { -it } },
            popEnterTransition = { slideInHorizontally { -it } },
            popExitTransition = { slideOutHorizontally { it } }
        ) {
            SettingsScreen(
                onNavigateToBack = { navController.popBackStack() },
                onLanguageChange = onLanguageChange
            )
        }
        composable<Destination.Analytics> {
            AnalyticsScreen(
                onNavigateToSettings = {
                    navController.navigate(Destination.Settings)
                },
                onNavigateToDiagrams = {
                    navController.navigate(Destination.Diagrams)
                }
            )
        }

        composable<Destination.Diagrams> {
            DiagramsScreen {
                navController.popBackStack()
            }
        }
    }
}