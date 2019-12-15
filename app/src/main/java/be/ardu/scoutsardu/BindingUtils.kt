package be.ardu.scoutsardu

import android.graphics.Color
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItemAantal

@BindingAdapter("naamWinkelwagenItem")
fun TextView.setNaamWinkelwagenItem(winkelwagenItemAantal: WinkelwagenItemAantal){
    winkelwagenItemAantal.let{
        text = it.item.naam
    }
}

@BindingAdapter("prijsWinkelwagenItem")
fun TextView.setPrijsWinkelwagenItem(winkelwagenItemAantal: WinkelwagenItemAantal){
    winkelwagenItemAantal.let{
        text = "€ " + it.item.prijs.toString()
    }
}

@BindingAdapter("aantalWinkelwagenItem")
fun EditText.setAantalWinkelwagenItem(winkelwagenItemAantal: WinkelwagenItemAantal){
    winkelwagenItemAantal.let{
        setText(winkelwagenItemAantal.aantal.toString(), TextView.BufferType.EDITABLE);
    }
}

@BindingAdapter("naamWinkelwagen")
fun TextView.setNaamWinkelwagen(winkelwagen: Winkelwagen){
    winkelwagen.let{
        text = winkelwagen.gebruiker.getFullNaam() + " (" + winkelwagen.getDatum() + " - " + winkelwagen.getTijd() + ")"
    }
}

@BindingAdapter("totaalPrijsWinkelwagen")
fun TextView.setTotaalPrijsWinkelwagen(winkelwagen: Winkelwagen){
    winkelwagen.let{
        text = winkelwagen.getTotaal().toString()
    }
}

@BindingAdapter("setAchtergrondKleur")
fun ConstraintLayout.setAchtergrond(winkelwagen: Winkelwagen){
    winkelwagen.let{
        if(it.betaald){
            setBackgroundColor(Color.GREEN)
        } else{
            setBackgroundColor(Color.RED)
        }

    }
}

