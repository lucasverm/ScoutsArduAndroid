package be.ardu.scoutsardu.models

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.ardu.scoutsardu.network.WinkelwagenItem
import java.io.Serializable

class Winkelwagen : Serializable {
    var winkelwagenItems = ArrayList<WinkelwagenItem>()
    var betaald = false;

    public fun getTotaal(): Double {
        var totaal = 0.00
        for (item in winkelwagenItems){
            totaal += item.prijs * item.aantal
        }
        return (Math.round(totaal * 100.0) / 100.0)
    }

}