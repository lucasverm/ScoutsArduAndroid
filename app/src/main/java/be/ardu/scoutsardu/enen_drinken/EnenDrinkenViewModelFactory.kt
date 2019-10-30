package be.ardu.scoutsardu.enen_drinken

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EnenDrinkenViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EnenDrinkenViewModel::class.java)) {
            return EnenDrinkenViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}