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
        var g = WinkelwagenItem(1, "cola", 0.65, 0)
        var g2 = WinkelwagenItem(2, "pils", 0.65, 0)
        var g3 = WinkelwagenItem(2, "fanta", 0.65, 0)


        _items.value = mutableListOf<WinkelwagenItem>(g, g2, g3)
        //_leden.value = mutableListOf<Gebruiker>()
    }

    fun onWinkelwagenItemClicked(winkelwagenItem: WinkelwagenItem){
        _navigateToCheckFragment.value = winkelwagenItem
    }

    fun onWinkelwagenToCheckFragmentNavigated(){
        _navigateToCheckFragment.value = null
    }
}