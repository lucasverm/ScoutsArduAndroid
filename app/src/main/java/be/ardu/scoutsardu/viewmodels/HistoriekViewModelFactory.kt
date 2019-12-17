package be.ardu.scoutsardu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.ardu.scoutsardu.repositories.HistoriekDatabaseRepository

class HistoriekViewModelFactory(
    private val localDatabaseRepository: HistoriekDatabaseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoriekViewModel::class.java)) {
            return HistoriekViewModel(localDatabaseRepository) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}