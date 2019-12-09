package be.ardu.scoutsardu.enen_drinken

import be.ardu.scoutsardu.models.WinkelwagenItem
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
    private val _items = MutableLiveData<List<WinkelwagenItem>>()
    private val _navigateToCheckFragment = MutableLiveData<WinkelwagenItem>()

    val items: LiveData<List<WinkelwagenItem>>
        get() = _items

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
        _items.value = lst
        getWinkelwagenItems()
    }


    fun getWinkelwagenItems() {

        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred = ScoutsArduApi.retrofitService.getWinkelwagenItems()
            try {
                // Await the completion of our Retrofit request
                var listResult = getPropertiesDeferred.await()
              println("Success: ${listResult.size} winkelwagen properties retrieved")
            } catch (e: Exception) {
                println("Failure: ${e.message}")
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