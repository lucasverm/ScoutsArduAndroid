package be.ardu.scoutsardu.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}