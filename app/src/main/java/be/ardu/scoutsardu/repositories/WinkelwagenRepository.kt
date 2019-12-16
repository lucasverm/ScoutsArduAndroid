package be.ardu.scoutsardu.repositories

import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WinkelwagenRepository {

    suspend fun getWinkelwagenItems(): ArrayList<WinkelwagenItemAantal> {
        val uitvoer = ArrayList<WinkelwagenItemAantal>()
        withContext(Dispatchers.IO) {
            val items = ScoutsArduApi.retrofitService.getWinkelwagenItems()

            for (item in items) {
                val w = WinkelwagenItemAantal(item, 0)
                uitvoer.add(w)
            }
        }
        return uitvoer
    }

    suspend fun getStamHistoriek(): ArrayList<Winkelwagen> {
        var uitvoer = ArrayList<Winkelwagen>()
        withContext(Dispatchers.IO) {
            uitvoer =
                ScoutsArduApi.retrofitService.getStamHistory(AccountRepository.bearerToken)
        }
        return uitvoer

    }

    suspend fun getMijnHistoriek(): ArrayList<Winkelwagen> {
        var uitvoer = ArrayList<Winkelwagen>()
        withContext(Dispatchers.IO) {
            uitvoer =
                ScoutsArduApi.retrofitService.getMijnHistory(AccountRepository.bearerToken)
        }
        return uitvoer

    }

    suspend fun postWinkelwagen(winkelwagen: Winkelwagen): Winkelwagen {
        var uitvoer = winkelwagen
        withContext(Dispatchers.IO) {
            uitvoer =
                ScoutsArduApi.retrofitService.postWinkelwagen(winkelwagen, AccountRepository.bearerToken)
        }
        return uitvoer

    }


}

