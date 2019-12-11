package be.ardu.scoutsardu.checkWinkelwagen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.network.Winkelwagen

class CheckWinkelwagenViewModel : ViewModel() {
    val winkelwagen = MutableLiveData<Winkelwagen>()


}
