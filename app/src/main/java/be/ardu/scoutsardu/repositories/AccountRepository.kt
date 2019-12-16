package be.ardu.scoutsardu.repositories

import be.ardu.scoutsardu.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountRepository {

    var bearerToken = ""
    var gebruiker: Gebruiker? = null

    suspend fun login(email: String, password: String): String {
        val data = SendLoginData(email, password)
        withContext(Dispatchers.IO) {
            bearerToken = "Bearer " + ScoutsArduApi.retrofitService.login(data)
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
        val data = SendRegistreerData(
            email,
            voornaam,
            achternaam,
            password
        )
        withContext(Dispatchers.IO) {
            bearerToken = "Bearer " + ScoutsArduApi.retrofitService.registreer(data)
        }
        return bearerToken
    }


    suspend fun getGebruiker(): Gebruiker {
        withContext(Dispatchers.IO) {
            gebruiker = ScoutsArduApi.retrofitService.getGebruiker(bearerToken)
        }
        return gebruiker!!
    }

    fun logout() {
        bearerToken = ""
        gebruiker = null
    }

    suspend fun putGebruiker(voornaam: String, achternaam: String, telefoon: String): Gebruiker {
        val data = PutGebruikerData(voornaam, achternaam)
        withContext(Dispatchers.IO) {
            gebruiker = ScoutsArduApi.retrofitService.putGebruiker(data, bearerToken)
        }
        return gebruiker!!
    }
}

