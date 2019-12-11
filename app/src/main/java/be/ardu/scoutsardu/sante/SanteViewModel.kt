package be.ardu.scoutsardu.sante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.lang.Exception
import java.lang.Thread.sleep

class SanteViewModel : ViewModel() {
    val winkelwagen = MutableLiveData<Winkelwagen>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    init {
        postWinkelwagen()
    }

    fun postWinkelwagen() {
        _status.value = ScoutsArduApiStatus.LOADING
        try {
            coroutineScope.launch(Dispatchers.Main) {
                winkelwagen.value = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.postWinkelwagen(winkelwagen.value!!)
                }.await()
                _status.value = ScoutsArduApiStatus.DONE
            }
        } catch (e: Throwable) {
            _status.value = ScoutsArduApiStatus.ERROR
        }


    }


}
