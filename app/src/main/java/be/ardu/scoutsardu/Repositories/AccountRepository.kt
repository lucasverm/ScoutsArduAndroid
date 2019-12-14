package be.ardu.scoutsardu.Repositories

import be.ardu.scoutsardu.network.Gebruiker
import be.ardu.scoutsardu.network.ScoutsArduApi
import be.ardu.scoutsardu.network.ScoutsArduApiStatus
import be.ardu.scoutsardu.network.SendLoginData

object AccountRepository {

    public var bearerToken = ""
    public var gebruiker: Gebruiker? = null

    suspend fun login(email: String, password: String): String {
        var data = SendLoginData(email,password)
        bearerToken = "Bearer "+ ScoutsArduApi.retrofitService.login(data)
        return bearerToken
    }

    suspend fun getGebruiker(): Gebruiker {
        gebruiker = ScoutsArduApi.retrofitService.getGebruiker(bearerToken)
        return gebruiker!!
    }

    fun logout() {
        bearerToken = ""
    }
}

