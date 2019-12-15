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
    var datumDag: Int,
    var datumMaand: Int,
    var datumJaar: Int,
    var datumUur: Int,
    var datumMinuten: Int,
    @Json(name="items")
    @SerializedName("items")
    var winkelwagenItems: ArrayList<WinkelwagenItemAantal>,
    var betaald: Boolean,
    var gebruiker: Gebruiker
): Serializable, Parcelable {

    fun getTotaal(): Double {
        var totaal = 0.00
        for (item in winkelwagenItems) {
            totaal += item.item.prijs * item.aantal
        }
        return (Math.round(totaal * 100.0) / 100.0)
    }

    fun getDatum(): String {
        return this.datumDag.toString() + "/" + this.datumMaand.toString() + "/" + this.datumJaar.toString()
    }

    fun getTijd(): String {
        return this.datumUur.toString() + ":" + this.datumMinuten.toString()
    }
}



