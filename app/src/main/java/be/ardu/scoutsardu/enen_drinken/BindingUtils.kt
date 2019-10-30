package be.ardu.scoutsardu.enen_drinken

import android.widget.TextView
import androidx.databinding.BindingAdapter
import be.ardu.scoutsardu.WinkelwagenItem

@BindingAdapter("naamWinkelwagenItem")
fun TextView.setNaamWinkelwagenItem(winkelwagenItem: WinkelwagenItem){
    winkelwagenItem?.let{
        text = winkelwagenItem.Naam
    }
}

@BindingAdapter("prijsWinkelwagenItem")
fun TextView.setPrijsWinkelwagenItem(winkelwagenItem: WinkelwagenItem){
    winkelwagenItem?.let{
        text = winkelwagenItem.Prijs.toString()
    }
}