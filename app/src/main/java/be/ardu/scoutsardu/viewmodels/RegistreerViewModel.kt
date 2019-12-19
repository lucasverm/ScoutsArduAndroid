package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.repositories.AccountRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegistreerViewModel : ViewModel(), KoinComponent {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val accountRepository: AccountRepository by inject()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        viewModelScope.cancel()
        _status.value = ScoutsArduApiStatus.ERROR
    }

    fun registreer(
        email: String,
        voornaam: String,
        achternaam: String,
        telefoonNummer: String,
        password: String,
        passwordBevestiging: String
    ) {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                accountRepository.registreer(
                    email,
                    voornaam,
                    achternaam,
                    telefoonNummer,
                    password,
                    passwordBevestiging
                )
                accountRepository.getGebruiker()
                _status.value = ScoutsArduApiStatus.DONE
                _status.value = ScoutsArduApiStatus.DEFAULT
            } catch (e: Exception) {
                println(e)
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }
}