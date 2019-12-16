package be.ardu.scoutsardu.repositories

import be.ardu.scoutsardu.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WinkelwagenRepository {

    suspend fun getWinkelwagenItems(): ArrayList<WinkelwagenItemAantal> {
        var uitvoer = ArrayList<WinkelwagenItemAantal>()
        withContext(Dispatchers.IO) {
            var items = ScoutsArduApi.retrofitService.getWinkelwagenItems().await()

            for (item in items) {
                var w = WinkelwagenItemAantal(item, 0)
                uitvoer.add(w)
            }
        }
        return uitvoer
    }

    suspend fun getStamHistoriek(): ArrayList<Winkelwagen> {
        var uitvoer = ArrayList<Winkelwagen>()
        withContext(Dispatchers.IO) {
            uitvoer =
                ScoutsArduApi.retrofitService.getStamHistory(AccountRepository.bearerToken).await()
        }
        return uitvoer

    }

    suspend fun getMijnHistoriek(): ArrayList<Winkelwagen> {
        var uitvoer = ArrayList<Winkelwagen>()
        withContext(Dispatchers.IO) {
            uitvoer =
                ScoutsArduApi.retrofitService.getMijnHistory(AccountRepository.bearerToken).await()
        }
        return uitvoer

    }

    suspend fun postWinkelwagen(winkelwagen: Winkelwagen): Winkelwagen {
        var uitvoer = winkelwagen
        withContext(Dispatchers.IO) {
            uitvoer =
                ScoutsArduApi.retrofitService.postWinkelwagen(winkelwagen, AccountRepository.bearerToken).await()
        }
        return uitvoer

    }


}

