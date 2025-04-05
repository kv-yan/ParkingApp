package am.android.parking

import am.android.parking.navigation.AppNavigation
import am.android.parking.settings.data.helper.LanguageChangeHelper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {

    private val languageChangeHelper by lazy { LanguageChangeHelper() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val currentLanguageCode: String =
                languageChangeHelper.getLanguageCode(applicationContext)

            var currentLanguage by remember { mutableStateOf(currentLanguageCode) }

            val onCurrentLanguageChange: (String) -> Unit = { newLanguage ->
                currentLanguage = newLanguage
                languageChangeHelper.changeLanguage(applicationContext, newLanguage)
            }

            AppNavigation(
                onLanguageChange = onCurrentLanguageChange
            )
        }
    }
}