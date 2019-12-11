package be.ardu.scoutsardu.checkWinkelwagen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.enen_drinken.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItem
import kotlinx.coroutines.*
import retrofit2.HttpException

class CheckWinkelwagenViewModel : ViewModel() {
    val winkelwagen = MutableLiveData<Winkelwagen>()

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun postWinkelwagen() {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                var winkelwagenOnline = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.postWinkelwagen(winkelwagen.value!!)
                }.await()

                println(winkelwagenOnline)
            } catch (e: HttpException) {
                println("fail " + e.code())
                println("fail " + e)
                println("fail " + e.message())
                println("fail " + e.response())
            }
        }

    }



}
