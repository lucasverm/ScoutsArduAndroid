package be.ardu.scoutsardu.enen_drinken

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.models.WinkelwagenItem
import be.ardu.scoutsardu.network.ScoutsArduApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnenDrinkenViewModel: ViewModel() {
    private val _items = MutableLiveData<List<WinkelwagenItem>>()
    private val _navigateToCheckFragment = MutableLiveData<WinkelwagenItem>()

    val items : LiveData<List<WinkelwagenItem>>
        get() = _items

    val navigateToCheckFragemt : LiveData<WinkelwagenItem>
        get() = _navigateToCheckFragment

    init {
        //
        var lst:ArrayList<WinkelwagenItem> = ArrayList<WinkelwagenItem>()
        for (x in 0..30) {
            var g = WinkelwagenItem(1, "cola", 0.65, 1)
            lst.add(g)
        }
        _items.value = lst
        getWinkelwagenItems()

        //_leden.value = mutableListOf<Gebruiker>()
    }

    fun getWinkelwagenItems(){
        println("---------------------------")
        ScoutsArduApi.retrofitService.getWinkelwagenItems().enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                println("---------------------------")
                println("Failure: " + t.message)
                // _items.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                println("---------------------------")
                println(response.body())
                //_items.value = response.body()
            }
        })
    }

    fun onWinkelwagenItemClicked(winkelwagenItem: WinkelwagenItem){
        _navigateToCheckFragment.value = winkelwagenItem
    }

    fun onWinkelwagenToCheckFragmentNavigated(){
        _navigateToCheckFragment.value = null
    }
}