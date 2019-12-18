package be.ardu.scoutsardu.repositories

import be.ardu.scoutsardu.network.ScoutsArduApiService
import be.ardu.scoutsardu.network.Winkelwagen
import be.ardu.scoutsardu.network.WinkelwagenItemAantal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class WinkelwagenRepository : KoinComponent {

    val accountRepository: AccountRepository by inject()
    val scoutsArduApiService: ScoutsArduApiService by inject()

    suspend fun getWinkelwagenItems(): ArrayList<WinkelwagenItemAantal> {
        val uitvoer = ArrayList<WinkelwagenItemAantal>()
        withContext(Dispatchers.IO) {
            val items = scoutsArduApiService.getWinkelwagenItems()

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
                scoutsArduApiService.getStamHistory(accountRepository.bearerToken)
        }
        return uitvoer

    }

    suspend fun getMijnHistoriek(): ArrayList<Winkelwagen> {
        var uitvoer = ArrayList<Winkelwagen>()
        withContext(Dispatchers.IO) {
            uitvoer =
                scoutsArduApiService.getMijnHistory(accountRepository.bearerToken)
        }
        return uitvoer

    }

    suspend fun postWinkelwagen(winkelwagen: Winkelwagen): Winkelwagen {
        var uitvoer = winkelwagen
        withContext(Dispatchers.IO) {
            uitvoer =
                scoutsArduApiService.postWinkelwagen(winkelwagen, accountRepository.bearerToken)
        }
        return uitvoer

    }


}

