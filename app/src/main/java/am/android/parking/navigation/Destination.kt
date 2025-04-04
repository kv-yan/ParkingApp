package am.android.parking.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Settings : Destination()

    @Serializable
    data object Analytics : Destination()
}