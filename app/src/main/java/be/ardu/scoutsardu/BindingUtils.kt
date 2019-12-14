package be.ardu.scoutsardu

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItem

@BindingAdapter("naamWinkelwagenItem")
fun TextView.setNaamWinkelwagenItem(winkelwagenItem: WinkelwagenItem){
    winkelwagenItem.let{
        text = winkelwagenItem.naam
    }
}

@BindingAdapter("prijsWinkelwagenItem")
fun TextView.setPrijsWinkelwagenItem(winkelwagenItem: WinkelwagenItem){
    winkelwagenItem.let{
        text = winkelwagenItem.prijs.toString()
    }
}

@BindingAdapter("aantalWinkelwagenItem")
fun EditText.setAantalWinkelwagenItem(winkelwagenItem: WinkelwagenItem){
    winkelwagenItem.let{
        setText(winkelwagenItem.aantal.toString(), TextView.BufferType.EDITABLE);
    }
}

@BindingAdapter("naamWinkelwagen")
fun TextView.setNaamWinkelwagen(winkelwagen: Winkelwagen){
    winkelwagen.let{
        text = winkelwagen.gebruiker.voornaam + " " +winkelwagen.gebruiker.achternaam + " (14/12/2019 - 13:13)"
    }
}

@BindingAdapter("totaalPrijsWinkelwagen")
fun TextView.setTotaalPrijsWinkelwagen(winkelwagen: Winkelwagen){
    winkelwagen.let{
        text = winkelwagen.getTotaal().toString()
    }
}
