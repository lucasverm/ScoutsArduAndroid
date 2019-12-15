package be.ardu.scoutsardu.registreer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegistreerViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistreerViewModel::class.java)) {
            return RegistreerViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}