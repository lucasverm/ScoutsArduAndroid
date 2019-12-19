package be.ardu.scoutsardu.repositories

import be.ardu.scoutsardu.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class AccountRepository : KoinComponent {

    private val scoutsArduApiService: ScoutsArduApiService by inject()

    var bearerToken = ""
    var gebruiker: Gebruiker? = null

    suspend fun login(email: String, password: String): String {
        val data = SendLoginData(email, password)
        withContext(Dispatchers.IO) {
            bearerToken = "Bearer " + scoutsArduApiService.login(data)
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
            password,
            passwordBevestiging,
            telefoonNummer
        )
        withContext(Dispatchers.IO) {
            bearerToken = "Bearer " + scoutsArduApiService.registreer(data)
        }
        return bearerToken
    }


    suspend fun getGebruiker(): Gebruiker {
        withContext(Dispatchers.IO) {
            gebruiker = scoutsArduApiService.getGebruiker(bearerToken)
        }
        return gebruiker!!
    }

    fun logout() {
        bearerToken = ""
        gebruiker = null
    }

    suspend fun putGebruiker(voornaam: String, achternaam: String, telefoon: String): Gebruiker {
        val data = PutGebruikerData(voornaam, achternaam,telefoon)
        withContext(Dispatchers.IO) {
            gebruiker = scoutsArduApiService.putGebruiker(data, bearerToken)
        }
        return gebruiker!!
    }
}

