package be.ardu.scoutsardu.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(
    tableName = "winkelwagen_historiek"
)
data class WinkelwagenDatabaseClass(

    @ColumnInfo(name = "datum_dag")
    var datumDag: Int,

    @ColumnInfo(name = "datum_maand")
    var datumMaand: Int,

    @ColumnInfo(name = "datum_jaar")
    var datumJaar: Int,

    @ColumnInfo(name = "datum_uur")
    var datumUur: Int,

    @ColumnInfo(name = "datum_minuten")
    var datumMinuten: Int,

    @ColumnInfo(name = "betaald")
    var betaald: Boolean,

    @ColumnInfo(name = "gebruikerVoornaam")
    var gebruikerVoornaam: String,

    @ColumnInfo(name = "gebruikerAchternaam")
    var gebruikerAchernaam: String,

    @ColumnInfo(name = "data")
    var data: String,

    @ColumnInfo(name = "type")
    var type: String
    ) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

    fun toPropertyObject(): Winkelwagen {

        return Winkelwagen(
            this.id,
            this.datumDag,
            this.datumMaand,
            this.datumJaar,
            this.datumUur,
            this.datumMinuten,
            Gson().fromJson<ArrayList<WinkelwagenItemAantal>>(data),
            this.betaald,
            Gebruiker(gebruikerVoornaam, gebruikerAchernaam, "", "", ArrayList())
        )
    }
}



