package be.ardu.scoutsardu.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.ardu.scoutsardu.database.WinkelwagenDatabaseDao

class HistoriekViewModelFactory(
    private val dataSource: WinkelwagenDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoriekViewModel::class.java)) {
            return HistoriekViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}