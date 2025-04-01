package am.android.parking

import am.android.parking.analytics.presentation.AnalyticsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.systemBarsDarkContentEnabled = true
            AnalyticsScreen()
        }
    }
}
