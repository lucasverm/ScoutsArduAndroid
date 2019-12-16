package be.ardu.scoutsardu.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize


/*var voornaam: String = ""
var achternaam: String = ""
//var foto: String = ""
var email: String = ""
var telefoonNummer: String = ""
var winkelwagens: [Winkelwagen] = []*/
data class Gebruiker(
    var voornaam: String,
    var achternaam: String,
    var email: String,
    @Json(name = "telNr")
    @SerializedName("telNr")
    var telefoonNummer: String,
    var winkelwagens: ArrayList<Winkelwagen>
) : Serializable, Parcelable {
    fun getFullNaam(): String {
        return this.voornaam.capitalize() + " " + this.achternaam.capitalize()
    }
}





