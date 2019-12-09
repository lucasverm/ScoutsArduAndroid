package be.ardu.scoutsardu.enen_drinken

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.models.WinkelwagenItem

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





        //_leden.value = mutableListOf<Gebruiker>()
    }

    fun onWinkelwagenItemClicked(winkelwagenItem: WinkelwagenItem){
        _navigateToCheckFragment.value = winkelwagenItem
    }

    fun onWinkelwagenToCheckFragmentNavigated(){
        _navigateToCheckFragment.value = null
    }
}