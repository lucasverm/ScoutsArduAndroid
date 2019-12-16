package be.ardu.scoutsardu.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AccountViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}