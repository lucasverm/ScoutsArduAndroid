package be.ardu.scoutsardu.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
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
        return voornaam.capitalize() + " " + achternaam.capitalize()
    }
}





