package be.ardu.scoutsardu.sante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SanteViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SanteViewModel::class.java)) {
            return SanteViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}