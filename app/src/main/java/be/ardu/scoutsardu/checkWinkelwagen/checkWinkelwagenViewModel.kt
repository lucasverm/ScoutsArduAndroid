package be.ardu.scoutsardu.checkWinkelwagen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.models.Winkelwagen
import be.ardu.scoutsardu.models.WinkelwagenItem

class CheckWinkelwagenViewModel : ViewModel() {
    val winkelwagen = MutableLiveData<Winkelwagen>()


}
