package be.ardu.scoutsardu.historiek

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.ardu.scoutsardu.checkWinkelwagen.CheckWinkelwagenViewModel

class HistoriekViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoriekViewModel::class.java)) {
            return HistoriekViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}