package be.ardu.scoutsardu.enen_drinken

import be.ardu.scoutsardu.network.WinkelwagenItem
import be.ardu.scoutsardu.network.ScoutsArduApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext



enum class ScoutsArduApiStatus { LOADING, ERROR, DONE }

class EnenDrinkenViewModel : ViewModel() {
    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    private val _items = MutableLiveData<List<WinkelwagenItem>>()
    private val _navigateToCheckFragment = MutableLiveData<WinkelwagenItem>()

    val items: LiveData<List<WinkelwagenItem>>
        get() = _items
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    val navigateToCheckFragemt: LiveData<WinkelwagenItem>
        get() = _navigateToCheckFragment

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        //
        var lst: ArrayList<WinkelwagenItem> = ArrayList<WinkelwagenItem>()
        for (x in 0..30) {
            var g = WinkelwagenItem(1, "cola", 0.65, 1)
            lst.add(g)
        }
        //_items.value = lst
        getWinkelwagenItems()
        getStamHistoriek()
    }


    fun getWinkelwagenItems() {
        _status.value = ScoutsArduApiStatus.LOADING
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getWinkelwagenItemsDeferred = ScoutsArduApi.retrofitService.getWinkelwagenItems()
            try {
                // Await the completion of our Retrofit request
                var listResult = getWinkelwagenItemsDeferred.await()
                if (listResult.size > 0) {
                    _items.value = listResult
                }
              println("Success: ${listResult.size} winkelwagenItems gevonden")
                _status.value = ScoutsArduApiStatus.DONE
            } catch (e: Exception) {
                _items.value =  ArrayList<WinkelwagenItem>()
                println("Failure: ${e.message}")
                _status.value = ScoutsArduApiStatus.ERROR

            }
        }

    }

    fun getStamHistoriek() {
       // [{"id":1,"items":[{"id":1,"naam":"Cola","prijs":1.0,"aantal":2,"winkelwagens":null},{"id":3,"naam":"Cola","prijs":1.0,"aantal":1,"winkelwagens":null}],"betaald":false,"datum":null,"gebruiker":null},{"id":2,"items":[{"id":1,"naam":"Cola","prijs":1.0,"aantal":2,"winkelwagens":null},{"id":2,"naam":"Bier","prijs":2.0,"aantal":1,"winkelwagens":null},{"id":8,"naam":"Bier","prijs":2.0,"aantal":1,"winkelwagens":null}],"betaald":false,"datum":null,"gebruiker":null}]
        /*ScoutsArduApi.retrofitService.getStamHistory().enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                println("fail " + t)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()
            }

        })
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getStamHistoriekDeferred = ScoutsArduApi.retrofitService.getStamHistory()
            try {
                // Await the completion of our Retrofit request
                var listResult = getStamHistoriekDeferred
                println("Success: ${listResult.size} winkelwagens gevonden")
            } catch (e: Exception) {
                println("Failure: ${e.message}")

            }
        }*/

        coroutineScope.launch(Dispatchers.Main) {
            try{
                var getPropertiesDeferred = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.getStamHistory()
                }.await()
                val listResult = getPropertiesDeferred
                println("succes: " + listResult.size)
            } catch (t: Throwable){
                println(t)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onWinkelwagenItemClicked(winkelwagenItem: WinkelwagenItem) {
        _navigateToCheckFragment.value = winkelwagenItem
    }

    fun onWinkelwagenToCheckFragmentNavigated() {
        _navigateToCheckFragment.value = null
    }
}