package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HistoriekViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoriekViewModel::class.java)) {
            return HistoriekViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}