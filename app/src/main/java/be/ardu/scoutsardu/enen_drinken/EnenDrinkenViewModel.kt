package be.ardu.scoutsardu.enen_drinken

import be.ardu.scoutsardu.network.WinkelwagenItem
import be.ardu.scoutsardu.network.ScoutsArduApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


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
    }


    fun getWinkelwagenItems() {
        _status.value = ScoutsArduApiStatus.LOADING
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred = ScoutsArduApi.retrofitService.getWinkelwagenItems()
            try {
                // Await the completion of our Retrofit request
                var listResult = getPropertiesDeferred.await()
                if (listResult.size > 0) {
                    _items.value = listResult
                }
              println("Success: ${listResult.size} winkelwagen properties retrieved")
                _status.value = ScoutsArduApiStatus.DONE
            } catch (e: Exception) {
                _items.value =  ArrayList<WinkelwagenItem>()
                println("Failure: ${e.message}")
                _status.value = ScoutsArduApiStatus.ERROR

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