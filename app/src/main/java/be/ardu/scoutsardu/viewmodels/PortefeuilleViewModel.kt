package be.ardu.scoutsardu.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.repositories.HistoriekDatabaseRepository
import be.ardu.scoutsardu.repositories.WinkelwagenRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class PortefeuilleViewModel: ViewModel(), KoinComponent {
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

    private val winkelwagenRepository: WinkelwagenRepository by inject()
    private val historiekDatabaseRepository: HistoriekDatabaseRepository by inject()

    init{
        getMijnHistoriek()
    }

    private fun getMijnHistoriek() {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                _winkelwagens.value = winkelwagenRepository.getMijnHistoriek()
                historiekDatabaseRepository.clearMijnHistoriek("mijnHistoriek")
                historiekDatabaseRepository.insertWinkelwagens(winkelwagens.value!!,"mijnHistoriek")
                _status.value = ScoutsArduApiStatus.DONE
            } catch (t: Exception) {
                _winkelwagens.value = historiekDatabaseRepository.getMijnHistoriek("mijnHistoriek")
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

    fun berekenTotaleSchuld():Double{
        var totaal = 0.0
        this.winkelwagens.value!!.forEach { winkelwagen ->
            if(!winkelwagen.betaald){
                totaal += winkelwagen.getTotaal()
            }
        }
        return totaal
    }

    fun onWinkelwagenItemClicked(winkelwagen: Winkelwagen) {
        _navigateToSanteFragment.value = winkelwagen
    }

    fun onWinkelwagenToSanteFragmentNavigated() {
        _navigateToSanteFragment.value = null
    }
}