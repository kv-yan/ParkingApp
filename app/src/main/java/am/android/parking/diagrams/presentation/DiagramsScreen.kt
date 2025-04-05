package am.android.parking.diagrams.presentation

import am.android.parking.R
import am.android.parking.common_presentation.ui.theme.ScreenBackground
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagramsScreen(
    modifier: Modifier = Modifier,
    onNavigateToBack: () -> Unit = {},
) {

    Scaffold(
        modifier = modifier,
        containerColor = ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.by_month),
                        fontFamily = FontFamily(Font(R.font.gilroy_semi_bold)),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ScreenBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(ScreenBackground)
                .padding(innerPadding)
        ) {
            ParkingOccupancyChart()
        }
    }
}