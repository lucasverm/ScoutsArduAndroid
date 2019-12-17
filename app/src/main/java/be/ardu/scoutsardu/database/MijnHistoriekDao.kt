package be.ardu.scoutsardu.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MijnHistoriekDao {
    @Insert
    fun insert(winkelwagen: WinkelwagenDatabaseClass)

    @Query("DELETE FROM winkelwagen_historiek WHERE type=:type")
    fun deleteHistoriek(type: String): Int

    @Query("SELECT * FROM winkelwagen_historiek WHERE type=:type")
    fun getHistoriek(type: String): List<WinkelwagenDatabaseClass>
}