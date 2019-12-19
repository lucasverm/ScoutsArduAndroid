package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.repositories.AccountRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class AccountViewModel : ViewModel(), KoinComponent {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val accountRepository: AccountRepository by inject()

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    fun wijzigingenOpslaan(voornaam: String, achternaam: String, telefoon: String) {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                accountRepository.putGebruiker(voornaam, achternaam, telefoon)
                _status.value = ScoutsArduApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    fun logout(){
        accountRepository.logout()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        viewModelScope.cancel()
        _status.value = ScoutsArduApiStatus.ERROR
    }

    fun getGebruiker(): Gebruiker {
        return accountRepository.gebruiker!!
    }
}