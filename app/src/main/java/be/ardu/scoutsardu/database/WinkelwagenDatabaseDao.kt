package be.ardu.scoutsardu.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WinkelwagenDatabaseDao {
    @Insert
    fun insert(winkelwagen: WinkelwagenDatabaseClass)

    @Update
    fun update(winkelwagen: WinkelwagenDatabaseClass)

    @Query("DELETE FROM winkelwagen_table")
    fun deleteAllWinkelwagens(): Int

    @Query("SELECT * FROM winkelwagen_table")
    fun getAllWinkelwagens(): ArrayList<WinkelwagenDatabaseClass>
}