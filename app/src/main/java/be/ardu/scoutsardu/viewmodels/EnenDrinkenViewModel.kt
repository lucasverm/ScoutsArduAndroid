package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import be.ardu.scoutsardu.repositories.WinkelwagenRepository
import kotlinx.coroutines.*

class EnenDrinkenViewModel : ViewModel() {
    private val _items = MutableLiveData<ArrayList<WinkelwagenItemAantal>>()
    val items: LiveData<ArrayList<WinkelwagenItemAantal>>
        get() = _items

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val _navigateToCheckFragment = MutableLiveData<WinkelwagenItemAantal>()

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getWinkelwagenItems()
    }

    private fun getWinkelwagenItems() {
        _status.value = ScoutsArduApiStatus.LOADING
        viewModelScope.launch {
            try {
                _items.value = WinkelwagenRepository.getWinkelwagenItems()
                _status.value = ScoutsArduApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        viewModelScope.cancel()
    }

    fun onWinkelwagenItemClicked(winkelwagenItemAantal: WinkelwagenItemAantal) {
        _navigateToCheckFragment.value = winkelwagenItemAantal
    }

}