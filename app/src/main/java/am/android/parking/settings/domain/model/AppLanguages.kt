package am.android.parking.settings.domain.model

import java.util.Locale

enum class AppLanguages(val language: String, val locale: Locale) {
    Arm("Հայերեն", Locale("hy")),
    Eng("English", Locale.ENGLISH),
    Rus("Русский", Locale("ru"));

    companion object {

        fun fromLocale(locale: Locale): AppLanguages = values().firstOrNull {

            it.locale.language == locale.language
        } ?: Eng
    }
}
