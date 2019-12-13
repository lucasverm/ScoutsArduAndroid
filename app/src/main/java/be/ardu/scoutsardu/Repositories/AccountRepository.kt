package be.ardu.scoutsardu.Repositories

import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.SendLoginData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.lang.Exception

class AccountRepository {

    private var bearerToken = ""
    suspend fun login(email: String, password: String): String {
        var data = SendLoginData(email,password)
        bearerToken = "Bearer "+ ScoutsArduApi.retrofitService.login(data)
        return bearerToken
    }
}
