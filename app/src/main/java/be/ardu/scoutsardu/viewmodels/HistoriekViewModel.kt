package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.repositories.HistoriekDatabaseRepository
import be.ardu.scoutsardu.repositories.WinkelwagenRepository
import kotlinx.coroutines.*

class HistoriekViewModel(
    var localDatabaseRepository: HistoriekDatabaseRepository
) : ViewModel() {
    private val _winkelwagens = MutableLiveData<ArrayList<Winkelwagen>>()
    val winkelwagens: LiveData<ArrayList<Winkelwagen>>
        get() = _winkelwagens

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val _navigateToSanteFragment = MutableLiveData<Winkelwagen>()
    val navigateToSanteFragemt: LiveData<Winkelwagen>
        get() = _navigateToSanteFragment

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMijnHistoriek()
    }

    fun getStamHistoriek() {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                _winkelwagens.value = WinkelwagenRepository.getStamHistoriek()
                localDatabaseRepository.clearMijnHistoriek("stamHistoriek")
                localDatabaseRepository.insertWinkelwagens(winkelwagens.value!!,"stamHistoriek")
                _status.value = ScoutsArduApiStatus.DONE
            } catch (t: Exception) {
                println(t)
                _winkelwagens.value = localDatabaseRepository.getMijnHistoriek("stamHistoriek")
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    fun getMijnHistoriek() {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                _winkelwagens.value = WinkelwagenRepository.getMijnHistoriek()
                localDatabaseRepository.clearMijnHistoriek("mijnHistoriek")
                localDatabaseRepository.insertWinkelwagens(winkelwagens.value!!,"mijnHistoriek")
                _status.value = ScoutsArduApiStatus.DONE
            } catch (t: Exception) {
                _winkelwagens.value = localDatabaseRepository.getMijnHistoriek("mijnHistoriek")
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

    fun onWinkelwagenItemClicked(winkelwagen: Winkelwagen) {
        _navigateToSanteFragment.value = winkelwagen
    }

    fun onWinkelwagenToSanteFragmentNavigated() {
        _navigateToSanteFragment.value = null
    }
}