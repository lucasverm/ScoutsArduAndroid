package be.ardu.scoutsardu.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class Winkelwagen(
    var id: Int,
    @Json(name="items")
    @SerializedName("items")
    var winkelwagenItems: ArrayList<WinkelwagenItem>,
    var betaald: Boolean,
    var gebruiker: Gebruiker
): Serializable, Parcelable {

    fun getTotaal(): Double {
        var totaal = 0.00
        for (item in winkelwagenItems) {
            totaal += item.prijs * item.aantal
        }
        return (Math.round(totaal * 100.0) / 100.0)
    }


}



