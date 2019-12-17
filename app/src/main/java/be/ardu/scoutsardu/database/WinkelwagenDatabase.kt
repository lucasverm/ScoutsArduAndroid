package be.ardu.scoutsardu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATABASE_NAME = "scouts-ardu-db"

@Database(entities = [WinkelwagenDatabaseClass::class], version = 2, exportSchema = false)
abstract class WinkelwagenDatabase : RoomDatabase() {

    abstract val winkelwagenDatabaseDao: WinkelwagenDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: WinkelwagenDatabase? = null

        fun getInstance(context: Context): WinkelwagenDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WinkelwagenDatabase::class.java,
                        "scouts_arde_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}