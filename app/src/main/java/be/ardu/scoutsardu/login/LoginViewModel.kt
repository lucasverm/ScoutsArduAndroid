package be.ardu.scoutsardu.login

import android.accounts.Account
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.ardu.scoutsardu.Repositories.AccountRepository
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel() {

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                AccountRepository.login(email, password)
                AccountRepository.getGebruiker()
                _status.value = ScoutsArduApiStatus.DONE
                _status.value = ScoutsArduApiStatus.DEFAULT
            } catch (e: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}
