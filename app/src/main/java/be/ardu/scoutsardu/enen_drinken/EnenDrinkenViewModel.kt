package be.ardu.scoutsardu.enen_drinken

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.WinkelwagenItem

class EnenDrinkenViewModel: ViewModel() {
    private val _items = MutableLiveData<List<WinkelwagenItem>>()
    val items : LiveData<List<WinkelwagenItem>>
        get() = _items


    init {
        var g = WinkelwagenItem(1, "cola", 0.65, 0)
        var g2 = WinkelwagenItem(2, "pils", 0.65, 0)
        //var g3 = WinkelwagenItem(2, "pils", 0.65, 0)


        _items.value = mutableListOf<WinkelwagenItem>(g, g2)
        //_leden.value = mutableListOf<Gebruiker>()
    }

    private val _navigateToCheckFragment = MutableLiveData<WinkelwagenItem>()
    val navigateToCheckFragemt : LiveData<WinkelwagenItem>
        get() = _navigateToCheckFragment

    fun onWinkelwagenItemClicked(winkelwagenItem: WinkelwagenItem){
        _navigateToCheckFragment.value = winkelwagenItem
    }

    fun onWinkelwagenToCheckFragmentNavigated(){
        _navigateToCheckFragment.value = null
    }
}