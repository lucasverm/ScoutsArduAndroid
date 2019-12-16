package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.repositories.AccountRepository
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.repositories.WinkelwagenRepository
import kotlinx.coroutines.*
import java.lang.Exception

class SanteViewModel : ViewModel() {
    val winkelwagen = MutableLiveData<Winkelwagen>()

    var winkelwagenIsHistory: Boolean = true

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    init {
    }

    fun postWinkelwagen() {
        _status.value = ScoutsArduApiStatus.LOADING
        viewModelScope.launch {
            try {
                winkelwagen.value = WinkelwagenRepository.postWinkelwagen(winkelwagen.value!!)
                _status.value = ScoutsArduApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
