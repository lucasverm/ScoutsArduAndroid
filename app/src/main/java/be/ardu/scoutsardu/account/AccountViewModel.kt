package be.ardu.scoutsardu.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.Repositories.AccountRepository
import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.putGebruikerData
import kotlinx.coroutines.*
import retrofit2.HttpException

class AccountViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
    get() = _status

    fun wijzigenOpslaan(voornaam: String, achternaam:String, telefoon:String) {
        var data = putGebruikerData(voornaam,achternaam,telefoon)
        coroutineScope.launch(Dispatchers.Main) {
            try {
                _status.value = ScoutsArduApiStatus.LOADING
                var getGebruiker = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.putGebruiker(data,AccountRepository.bearerToken)
                }.await()
                AccountRepository.gebruiker = getGebruiker.await()
                _status.value = ScoutsArduApiStatus.DONE
            } catch (t: HttpException) {
                println(t.code())
                println(t.response())
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