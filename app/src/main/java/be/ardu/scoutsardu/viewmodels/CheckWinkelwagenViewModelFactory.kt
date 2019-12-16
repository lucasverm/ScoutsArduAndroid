package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CheckWinkelwagenViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckWinkelwagenViewModel::class.java)) {
            return CheckWinkelwagenViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}