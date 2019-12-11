package be.ardu.scoutsardu.enen_drinken

import be.ardu.scoutsardu.network.WinkelwagenItem
import be.ardu.scoutsardu.network.ScoutsArduApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import kotlinx.coroutines.*

class EnenDrinkenViewModel : ViewModel() {

    private val _items = MutableLiveData<List<WinkelwagenItem>>()
    val items: LiveData<List<WinkelwagenItem>>
        get() = _items

    private val _status = MutableLiveData<ScoutsArduApiStatus>()
    val status: LiveData<ScoutsArduApiStatus>
        get() = _status

    private val _navigateToCheckFragment = MutableLiveData<WinkelwagenItem>()
    val navigateToCheckFragemt: LiveData<WinkelwagenItem>
        get() = _navigateToCheckFragment

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getWinkelwagenItems()
        //getStamHistoriek()
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
                var listResult = getWinkelwagenItemsDeferred.await()
                if (listResult.size > 0) {
                    _items.value = listResult
                }
                _status.value = ScoutsArduApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ScoutsArduApiStatus.ERROR

            }
        }

    }


    fun getStamHistoriek() {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                var getStamHistoryDeferred = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.getStamHistory()
                }.await()
                var listResult = getStamHistoryDeferred.await()

            } catch (t: Exception) {
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