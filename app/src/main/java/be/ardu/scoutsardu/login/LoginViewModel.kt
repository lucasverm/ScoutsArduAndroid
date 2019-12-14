package be.ardu.scoutsardu.login

import android.accounts.Account
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.Repositories.AccountRepository
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope{

    private var viewModelJob = Job()
    override val coroutineContext: CoroutineContext
        get() = viewModelJob + Dispatchers.Main

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun login(email:String, password:String) {
        launch(Dispatchers.Main) {
            try{
                _status.value = ScoutsArduApiStatus.LOADING
                var getPropertiesDeferred = async(Dispatchers.IO) {
                    AccountRepository.login(email,password)
                    AccountRepository.getGebruiker()
                }.await()
                _status.value = ScoutsArduApiStatus.DONE
                _status.value = ScoutsArduApiStatus.DEFAULT
            } catch (e: Exception){
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }
}
