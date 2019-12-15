package be.ardu.scoutsardu.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WinkelwagenItem(
    var id: Int,
    var naam: String,
    var prijs: Double
):Parcelable {

}