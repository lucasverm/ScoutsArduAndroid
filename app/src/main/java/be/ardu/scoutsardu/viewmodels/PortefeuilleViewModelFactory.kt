package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.ardu.scoutsardu.repositories.HistoriekDatabaseRepository

class PortefeuilleViewModelFactory(
    private val localDatabaseRepository: HistoriekDatabaseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortefeuilleViewModel::class.java)) {
            return PortefeuilleViewModel(localDatabaseRepository) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}