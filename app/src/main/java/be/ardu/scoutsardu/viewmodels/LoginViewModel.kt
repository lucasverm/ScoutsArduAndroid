package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.repositories.AccountRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginViewModel : ViewModel(), KoinComponent {

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val accountRepository: AccountRepository by inject()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                accountRepository.login(email, password)
                accountRepository.getGebruiker()
                _status.value = ScoutsArduApiStatus.DONE
                _status.value = ScoutsArduApiStatus.DEFAULT
            } catch (e: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        viewModelScope.cancel()
    }

}
