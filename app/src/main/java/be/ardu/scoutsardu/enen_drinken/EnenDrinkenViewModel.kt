package be.ardu.scoutsardu.enen_drinken

import be.ardu.scoutsardu.network.ScoutsArduApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import kotlinx.coroutines.*

class EnenDrinkenViewModel : ViewModel() {
    private val _items = MutableLiveData<ArrayList<WinkelwagenItemAantal>>()
    val items: LiveData<ArrayList<WinkelwagenItemAantal>>
        get() = _items

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val _navigateToCheckFragment = MutableLiveData<WinkelwagenItemAantal>()
    val navigateToCheckFragemt: LiveData<WinkelwagenItemAantal>
        get() = _navigateToCheckFragment

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getWinkelwagenItems()
    }

    fun getWinkelwagenItems() {
        _status.value = ScoutsArduApiStatus.LOADING
        coroutineScope.launch(Dispatchers.Main) {
            try {
                // Get the Deferred object for our Retrofit request
                var getWinkelwagenItemsDeferred = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.getWinkelwagenItems()
                }.await()
                // Await the completion of our Retrofit request
                var winkelWagenItems = getWinkelwagenItemsDeferred.await()
                var itemsToPublish = ArrayList<WinkelwagenItemAantal>()
                for (item in winkelWagenItems) {
                    var w = WinkelwagenItemAantal(item, 0)
                    itemsToPublish.add(w)
                }
                _items.value = itemsToPublish
                _status.value = ScoutsArduApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR

            }

        }

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onWinkelwagenItemClicked(winkelwagenItemAantal: WinkelwagenItemAantal) {
        _navigateToCheckFragment.value = winkelwagenItemAantal
    }

    fun onWinkelwagenToCheckFragmentNavigated() {
        _navigateToCheckFragment.value = null
    }
}