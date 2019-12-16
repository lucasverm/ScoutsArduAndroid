package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.repositories.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    fun wijzigingenOpslaan(voornaam: String, achternaam: String, telefoon: String) {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                AccountRepository.putGebruiker(voornaam, achternaam, telefoon)
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

    fun getGebruiker(): Gebruiker {
        return AccountRepository.gebruiker!!
    }
}