package be.ardu.scoutsardu.enen_drinken

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import be.ardu.scoutsardu.models.WinkelwagenItem

@BindingAdapter("naamWinkelwagenItem")
fun TextView.setNaamWinkelwagenItem(winkelwagenItem: WinkelwagenItem){
    winkelwagenItem.let{
        text = winkelwagenItem.Naam
    }
}

@BindingAdapter("prijsWinkelwagenItem")
fun TextView.setPrijsWinkelwagenItem(winkelwagenItem: WinkelwagenItem){
    winkelwagenItem.let{
        text = winkelwagenItem.Prijs.toString()
    }
}

@BindingAdapter("aantalWinkelwagenItem")
fun EditText.setAantalWinkelwagenItem(winkelwagenItem: WinkelwagenItem){
    winkelwagenItem.let{
        setText(winkelwagenItem.Aantal.toString(), TextView.BufferType.EDITABLE);
    }
}