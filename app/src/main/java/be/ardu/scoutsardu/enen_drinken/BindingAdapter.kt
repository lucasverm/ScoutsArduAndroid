package be.ardu.scoutsardu.enen_drinken

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import be.ardu.scoutsardu.R

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