package be.ardu.scoutsardu.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.ardu.scoutsardu.database.WinkelwagenDatabaseClass
import be.ardu.scoutsardu.database.WinkelwagenDatabaseDao
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.repositories.WinkelwagenRepository
import kotlinx.coroutines.*

class HistoriekViewModel(
    val database: WinkelwagenDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
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

    fun getDatabaseHistroiek() {
        viewModelScope.launch {
            //_winkelwagens.value = getHistoriekWinkelwagensFromDataBase()
            println(getHistoriekWinkelwagensFromDataBase())
        }
    }

    private suspend fun getHistoriekWinkelwagensFromDataBase(): ArrayList<Winkelwagen> {
        return withContext(Dispatchers.IO) {
            var winkelwagens = database.getAllWinkelwagens()
            winkelwagens.value!!
        }
    }

    fun zetWinkelwagensInDatabase(winkelwagens: ArrayList<Winkelwagen>) {
        viewModelScope.launch {
            for (item in winkelwagens) {
                var w = WinkelwagenDatabaseClass(item.id,item.datumDag, item.datumMaand, item.datumJaar, item.datumUur,item.datumMinuten, item.betaald)
                insertAWinkelwagen(w)
            }
        }
    }

    private suspend fun insertAWinkelwagen(winkelwagenDatabaseClass: WinkelwagenDatabaseClass) {
        withContext(Dispatchers.IO) {
            database.insert(winkelwagenDatabaseClass)
        }
    }

    fun getStamHistoriek() {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                _winkelwagens.value = WinkelwagenRepository.getStamHistoriek()
                _status.value = ScoutsArduApiStatus.DONE
            } catch (t: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    fun getMijnHistoriek() {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                _winkelwagens.value = WinkelwagenRepository.getMijnHistoriek()
                _status.value = ScoutsArduApiStatus.DONE
                zetWinkelwagensInDatabase(winkelwagens.value!!)
                getDatabaseHistroiek()
            } catch (t: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        viewModelScope.cancel()
    }

    fun onWinkelwagenItemClicked(winkelwagen: Winkelwagen) {
        _navigateToSanteFragment.value = winkelwagen
    }

    fun onWinkelwagenToSanteFragmentNavigated() {
        _navigateToSanteFragment.value = null
    }

    fun verwijderAlleWinkelwagens(){
        viewModelScope.launch {
            verwijderAlleWinkelwagensSuspend()
        }
    }

    fun verwijderAlleWinkelwagensSuspend(){
        viewModelScope.launch {
            database.deleteAllWinkelwagens()
        }
    }
}