package am.android.parking.navigation

import am.android.parking.MainActivity
import am.android.parking.analytics.presentation.AnalyticsScreen
import am.android.parking.settings.presentation.SettingsScreen
import am.android.parking.settings.presentation.SettingsViewModel
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startDestination: Destination = Destination.Analytics,
    onLanguageChange: (String) -> Unit = {},
) {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val currentDestination by navController.currentBackStackEntryAsState()

    systemUiController.systemBarsDarkContentEnabled = true

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
                }
            )
        }
    }
}