package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import be.ardu.scoutsardu.repositories.AccountRepository
import be.ardu.scoutsardu.repositories.WinkelwagenRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class EnenDrinkenViewModel : ViewModel(), KoinComponent {
    private val _items = MutableLiveData<ArrayList<WinkelwagenItemAantal>>()
    val items: LiveData<ArrayList<WinkelwagenItemAantal>>
        get() = _items

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val _navigateToCheckFragment = MutableLiveData<WinkelwagenItemAantal>()

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val accountRepository: AccountRepository by inject()
    private val winkelwagenRepository: WinkelwagenRepository by inject()

    init {
        getWinkelwagenItems()
    }

    fun getGebruiker(): Gebruiker {
        return accountRepository.gebruiker!!
    }

    private fun getWinkelwagenItems() {
        _status.value = ScoutsArduApiStatus.LOADING
        viewModelScope.launch {
            try {
                _items.value = winkelwagenRepository.getWinkelwagenItems()
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
        _status.value = ScoutsArduApiStatus.ERROR
    }

    fun onWinkelwagenItemClicked(winkelwagenItemAantal: WinkelwagenItemAantal) {
        _navigateToCheckFragment.value = winkelwagenItemAantal
    }

}