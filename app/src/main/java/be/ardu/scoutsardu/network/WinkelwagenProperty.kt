package be.ardu.scoutsardu.network

import android.os.Parcelable
import be.ardu.scoutsardu.database.WinkelwagenDatabaseClass
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*
import kotlin.math.roundToLong

@Parcelize
data class Winkelwagen(
    var id: Int,
    var datumDag: Int,
    var datumMaand: Int,
    var datumJaar: Int,
    var datumUur: Int,
    var datumMinuten: Int,
    @Json(name = "items")
    @SerializedName("items")
    var winkelwagenItems: ArrayList<WinkelwagenItemAantal>,
    var betaald: Boolean,
    var gebruiker: Gebruiker
) : Serializable, Parcelable {

    fun getTotaal(): Double {
        var totaal = 0.00
        for (item in winkelwagenItems) {
            totaal += item.item.prijs * item.aantal
        }
        return ((totaal * 100.0).roundToLong() / 100.0)
    }

    fun getDatum(): String {
        val dag = addAdditionalZero(datumDag.toString())
        val maand = addAdditionalZero(datumMaand.toString())
        return dag + "/" + maand + "/" + this.datumJaar.toString()
    }

    fun getTijd(): String {
        val uur = addAdditionalZero(datumUur.toString())
        val minuten = addAdditionalZero(datumMinuten.toString())
        return "$uur:$minuten"
    }

    fun addAdditionalZero(item: String): String {
        var uitvoer = ""
        if (item.length == 1) {
            uitvoer += "0"
        }
        uitvoer += item
        return uitvoer
    }

    fun toDatabaseObject(type: String): WinkelwagenDatabaseClass {
        return WinkelwagenDatabaseClass(
            this.datumDag,
            this.datumMaand,
            this.datumJaar,
            this.datumUur,
            this.datumMinuten,
            this.betaald,
            this.gebruiker.voornaam,
            this.gebruiker.achternaam,
            Gson().toJson(winkelwagenItems),
            type)
    }
}



