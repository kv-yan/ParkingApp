package am.android.parking.settings.presentation

import am.android.parking.settings.domain.model.AppLanguages
import am.android.parking.settings.domain.usecase.GetLanguageUseCase
import am.android.parking.settings.domain.usecase.SaveLanguageUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val saveLanguageUseCase: SaveLanguageUseCase,
    getLanguageUseCase: GetLanguageUseCase,
) : ViewModel() {

    private val _languages = MutableStateFlow<List<AppLanguages>>(listOf()).apply {
        value = AppLanguages.entries
    }
    val languages = _languages.asStateFlow()

    private val _selectedLanguage = MutableStateFlow<AppLanguages>(AppLanguages.Eng)
    val selectedLanguage = _selectedLanguage.asStateFlow()

    init {
        getLanguageUseCase().onEach { lang ->
            _selectedLanguage.value = lang
        }.launchIn(viewModelScope)
    }

    fun setSelectedLanguage(language: AppLanguages) {
        _selectedLanguage.value = language
        viewModelScope.launch(Dispatchers.IO) {
            saveLanguageUseCase(language)
        }
    }
}
