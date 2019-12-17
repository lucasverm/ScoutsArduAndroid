package be.ardu.scoutsardu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATABASE_NAME = "scouts-ardu-db"

@Database(entities = [WinkelwagenDatabaseClass::class], version = 4, exportSchema = false)
abstract class ScoutsArduDatabase : RoomDatabase() {

    abstract val mijnHistoriek: MijnHistoriekDao

    companion object {
        @Volatile
        private var INSTANCE: ScoutsArduDatabase? = null

        fun getInstance(context: Context): ScoutsArduDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ScoutsArduDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}