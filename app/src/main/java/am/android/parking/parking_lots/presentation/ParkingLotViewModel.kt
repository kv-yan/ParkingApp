package am.android.parking.parking_lots.presentation

import am.android.parking.parking_lots.domain.model.ParkingLotStatus
import am.android.parking.parking_lots.domain.repository.ParkingLotsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ParkingLotViewModel(
    private val parkingLotsRepository: ParkingLotsRepository,
) : ViewModel() {

    private val _parkingLotStatus = MutableStateFlow<ParkingLotStatus?>(null)
    val parkingLotStatus = _parkingLotStatus.asStateFlow()

    init {
        fetchParkingLotStatus()
    }

    private fun fetchParkingLotStatus() {
        parkingLotsRepository.getParkingLots().onEach { status ->
            _parkingLotStatus.value = status
        }.launchIn(viewModelScope)
    }
}

