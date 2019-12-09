package be.ardu.scoutsardu.checkWinkelwagen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.ardu.scoutsardu.models.Winkelwagen

class CheckWinkelwagenViewModel : ViewModel() {
    val winkelwagen = MutableLiveData<Winkelwagen>()


}
