package be.ardu.scoutsardu.registreer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.Repositories.AccountRepository
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegistreerViewModel : ViewModel(), CoroutineScope {

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

    fun registreer(email:String, voornaam:String, achternaam:String, telefoonNummer:String, password:String, passwordBevestiging:String) {
        launch(Dispatchers.Main) {
            try{
                _status.value = ScoutsArduApiStatus.LOADING
                var register = async(Dispatchers.IO) {
                    AccountRepository.registreer(email,voornaam,achternaam,telefoonNummer,password,passwordBevestiging)
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