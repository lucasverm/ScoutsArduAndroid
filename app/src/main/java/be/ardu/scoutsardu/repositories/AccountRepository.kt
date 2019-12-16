package be.ardu.scoutsardu.repositories

import be.ardu.scoutsardu.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountRepository {

    public var bearerToken = ""
    public var gebruiker: Gebruiker? = null

    suspend fun login(email: String, password: String): String {
        var data = SendLoginData(email, password)
        withContext(Dispatchers.IO) {
            bearerToken = "Bearer " + ScoutsArduApi.retrofitService.login(data).await()
        }
        return bearerToken
    }

    suspend fun registreer(
        email: String,
        voornaam: String,
        achternaam: String,
        telefoonNummer: String,
        password: String,
        passwordBevestiging: String
    ): String {
        var data = SendRegistreerData(
            email,
            voornaam,
            achternaam,
            telefoonNummer,
            password,
            passwordBevestiging
        )
        withContext(Dispatchers.IO) {
            bearerToken = "Bearer " + ScoutsArduApi.retrofitService.registreer(data).await()
        }
        return bearerToken
    }


    suspend fun getGebruiker(): Gebruiker {
        withContext(Dispatchers.IO) {
            gebruiker = ScoutsArduApi.retrofitService.getGebruiker(bearerToken).await()
        }
        return gebruiker!!
    }

    fun logout() {
        bearerToken = ""
        gebruiker = null
    }
}

