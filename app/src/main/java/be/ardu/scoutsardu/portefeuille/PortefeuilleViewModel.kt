package be.ardu.scoutsardu.portefeuille


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.Repositories.AccountRepository
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import kotlinx.coroutines.*

class PortefeuilleViewModel : ViewModel() {
    private val _winkelwagens = MutableLiveData<ArrayList<Winkelwagen>>()
    val winkelwagens: LiveData<ArrayList<Winkelwagen>>
        get() = _winkelwagens

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val _navigateToSanteFragment = MutableLiveData<Winkelwagen>()
    val navigateToSanteFragemt: LiveData<Winkelwagen>
        get() = _navigateToSanteFragment

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init{
        getMijnHistoriek()
    }

    fun getMijnHistoriek() {
        _winkelwagens.value?.clear()
        coroutineScope.launch(Dispatchers.Main) {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                var getStamHistoryDeferred = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.getMijnHistory(AccountRepository.bearerToken)
                }.await()
                _winkelwagens.value = getStamHistoryDeferred.await()
                _status.value = ScoutsArduApiStatus.DONE
            } catch (t: Exception) {
                println(t)
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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