package be.ardu.scoutsardu.adapters

import android.graphics.Color
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import be.ardu.scoutsardu.utilities.FontAwesomeTextViewSolid
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
    setText(winkelwagenItemAantal.aantal.toString(), TextView.BufferType.EDITABLE)
}

@BindingAdapter("naamAndDatumWinkelwagen")
fun TextView.setNaamWinkelwagen(winkelwagen: Winkelwagen){
    winkelwagen.let{
        text = winkelwagen.gebruiker.getFullNaam() + " (" + winkelwagen.getDatum() + " - " + winkelwagen.getTijd() + ")"
    }
}

@BindingAdapter("datumWinkelwagen")
fun TextView.setDatumWinkelwagen(winkelwagen: Winkelwagen){
    winkelwagen.let{
        text = winkelwagen.getDatum() + " - " + winkelwagen.getTijd()
    }
}

@BindingAdapter("totaalPrijsWinkelwagen")
fun TextView.setTotaalPrijsWinkelwagen(winkelwagen: Winkelwagen){
    winkelwagen.let{
        text = "€ " + winkelwagen.getTotaal().toString()
    }
}

@BindingAdapter("setIcon")
fun FontAwesomeTextViewSolid.setIcon(winkelwagen: Winkelwagen){
    winkelwagen.let{
        if(it.betaald){
            text = "\uf00c"
            setTextColor(Color.GREEN)
        } else{
            text = "\uf00d"
            setTextColor(Color.RED)
        }


    }
}

