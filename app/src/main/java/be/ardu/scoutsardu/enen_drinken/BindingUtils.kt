package be.ardu.scoutsardu.enen_drinken

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import be.ardu.scoutsardu.R
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

@BindingAdapter("ScoutsArduApiStatus")
fun bindStatus(statusImageView: ImageView, status: ScoutsArduApiStatus?){
    when(status){
        ScoutsArduApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ScoutsArduApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ScoutsArduApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}