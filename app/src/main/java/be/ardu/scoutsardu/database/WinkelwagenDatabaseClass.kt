package be.ardu.scoutsardu.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.Winkelwagen

@Entity(tableName = "winkelwagen_table")
data class WinkelwagenDatabaseClass(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

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
    var gebruikerAchernaam: String
) {

    fun toPropertyObject(): Winkelwagen {
        return Winkelwagen(
            this.id,
            this.datumDag,
            this.datumMaand,
            this.datumJaar,
            this.datumUur,
            this.datumMinuten,
            ArrayList(),
            this.betaald,
            Gebruiker(gebruikerVoornaam, gebruikerAchernaam, "", "", ArrayList())
        )
    }
}



