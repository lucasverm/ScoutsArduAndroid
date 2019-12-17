package be.ardu.scoutsardu.repositories

import android.app.Application
import be.ardu.scoutsardu.database.ScoutsArduDatabase
import be.ardu.scoutsardu.database.WinkelwagenDatabaseClass
import be.ardu.scoutsardu.network.Winkelwagen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class HistoriekDatabaseRepository(application: Application) {

    var database = ScoutsArduDatabase.getInstance(application).mijnHistoriek

    suspend fun clearMijnHistoriek(type:String) {
        withContext(Dispatchers.IO) {
            database.deleteHistoriek(type)
        }
    }

    suspend fun getMijnHistoriek(type:String): ArrayList<Winkelwagen> {
        return withContext(Dispatchers.IO) {
            var uitvoer = ArrayList<Winkelwagen>()
            for(item in database.getHistoriek(type)){
                uitvoer.add(item.toPropertyObject())
            }
            Collections.reverse(uitvoer)
            uitvoer
        }
    }

    suspend fun insertWinkelwagens(winkelwagens: ArrayList<Winkelwagen>, type: String) {
        for (item in winkelwagens) {
            insertWinkelwagen(item.toDatabaseObject(type))
        }
    }

    suspend fun insertWinkelwagen(winkelwagenDatabaseClass: WinkelwagenDatabaseClass) {
        withContext(Dispatchers.IO) {
            database.insert(winkelwagenDatabaseClass)
        }
    }
}