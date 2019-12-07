package be.ardu.scoutsardu.models

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

class Winkelwagen : Serializable {
    var winkelwagenItems = ArrayList<WinkelwagenItem>()

    var betaald = false;

}