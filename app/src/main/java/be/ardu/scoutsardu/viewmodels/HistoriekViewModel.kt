package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.repositories.LocalDatabaseRepository
import be.ardu.scoutsardu.repositories.WinkelwagenRepository
import kotlinx.coroutines.*

class HistoriekViewModel(
    var localDatabaseRepository: LocalDatabaseRepository
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
                _status.value = ScoutsArduApiStatus.DONE
            } catch (t: Exception) {
                _winkelwagens.value = ArrayList()
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    fun getMijnHistoriek() {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                _winkelwagens.value = WinkelwagenRepository.getMijnHistoriek()
                localDatabaseRepository.clearMijnHistoriek()
                localDatabaseRepository.insertWinkelwagens(winkelwagens.value!!)
                _status.value = ScoutsArduApiStatus.DONE
            } catch (t: Exception) {
                _winkelwagens.value = localDatabaseRepository.getMijnHistoriek()
                println(winkelwagens.value!!.count())
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