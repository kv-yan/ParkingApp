package am.android.parking.settings.presentation

import am.android.parking.R
import am.android.parking.analytics.presentation.AnalyticsCard
import am.android.parking.common_presentation.ui.theme.ScreenBackground
import am.android.parking.common_presentation.ui.theme.ValueColor
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = koinViewModel(),
    onNavigateToBack: () -> Unit = {},
    onLanguageChange: (String) -> Unit = {},
) {
    val languages by settingsViewModel.languages.collectAsState()
    val selectedLanguage by settingsViewModel.selectedLanguage.collectAsState()

    Scaffold(
        modifier = modifier,
        containerColor = ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ValueColor,
                    titleContentColor = Color.White,
                )
            )
        }
    ) { padding ->
        AnalyticsCard(
            modifier = Modifier
                .padding(padding)
                .padding(top = 16.dp),
            title = stringResource(R.string.language),
        ) {
            LanguageSelector(
                modifier = Modifier.fillMaxWidth(),
                languages = languages,
                selectedLanguage = selectedLanguage,
                onLanguageSelected = { language ->
                    settingsViewModel.setSelectedLanguage(language)
                    println(
                        "Selected language: ${language.locale.language}"
                    )
                    onLanguageChange(language.locale.language)
                }
            )
        }
    }
}