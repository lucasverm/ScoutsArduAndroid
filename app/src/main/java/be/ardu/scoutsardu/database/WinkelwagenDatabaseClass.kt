package be.ardu.scoutsardu.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "winkelwagen_table")
data class WinkelwagenDatabaseClass(
    @PrimaryKey (autoGenerate = true)
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
    var betaald: Boolean

){

    init{

    }
}



