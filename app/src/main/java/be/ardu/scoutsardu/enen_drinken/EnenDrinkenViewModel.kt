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

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        //
        var lst: ArrayList<WinkelwagenItem> = ArrayList<WinkelwagenItem>()
        for (x in 0..30) {
            var g = WinkelwagenItem(1, "cola", 0.65, 1)
            lst.add(g)
        }
        getWinkelwagenItems()
        getStamHistoriek()
    }


    fun getWinkelwagenItems() {
        _status.value = ScoutsArduApiStatus.LOADING
        coroutineScope.launch(Dispatchers.Main) {
            try {
                var listResult = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.getWinkelwagenItems()
                }.await()
                if (listResult.size > 0) {
                    _items.value = listResult
                }
                _status.value = ScoutsArduApiStatus.DONE
            } catch (e: Exception) {
                _items.value = ArrayList<WinkelwagenItem>()
                _status.value = ScoutsArduApiStatus.ERROR
            }
        }

    }

    fun getStamHistoriek() {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                var listResult = async(Dispatchers.IO) {
                    ScoutsArduApi.retrofitService.getStamHistory()
                }.await()
            } catch (t: Throwable) {
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