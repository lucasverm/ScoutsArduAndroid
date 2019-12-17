package be.ardu.scoutsardu.repositories

import android.app.Application
import be.ardu.scoutsardu.database.WinkelwagenDatabase
import be.ardu.scoutsardu.database.WinkelwagenDatabaseClass
import be.ardu.scoutsardu.network.Winkelwagen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDatabaseRepository(application: Application) {

    val database = WinkelwagenDatabase.getInstance(application).winkelwagenDatabaseDao

    suspend fun clearMijnHistoriek() {
        withContext(Dispatchers.IO) {
            database.deleteAllWinkelwagens()
        }
    }

    suspend fun getMijnHistoriek(): ArrayList<Winkelwagen> {
        return withContext(Dispatchers.IO) {
            var uitvoer = ArrayList<Winkelwagen>()
            for(item in database.getAllWinkelwagens()){
                uitvoer.add(item.toPropertyObject())
            }
            uitvoer
        }
    }

    suspend fun insertWinkelwagens(winkelwagens: ArrayList<Winkelwagen>) {
        for (item in winkelwagens) {
            insertWinkelwagen(item.toDatabaseObject())
        }
    }

    suspend fun insertWinkelwagen(winkelwagenDatabaseClass: WinkelwagenDatabaseClass) {
        withContext(Dispatchers.IO) {
            database.insert(winkelwagenDatabaseClass)
        }
    }
}