package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PortefeuilleViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortefeuilleViewModel::class.java)) {
            return PortefeuilleViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}