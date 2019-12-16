package be.ardu.scoutsardu.Repositories

import be.ardu.scoutsardu.network.*

object AccountRepository {

    public var bearerToken = ""
    public var gebruiker: Gebruiker? = null

    suspend fun login(email: String, password: String): String {
        var data = SendLoginData(email, password)
        bearerToken = "Bearer " + ScoutsArduApi.retrofitService.login(data)
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

        bearerToken = "Bearer " + ScoutsArduApi.retrofitService.registreer(data)
        return bearerToken
    }


    suspend fun getGebruiker(): Gebruiker {
        gebruiker = ScoutsArduApi.retrofitService.getGebruiker(bearerToken)
        return gebruiker!!
    }

    fun logout() {
        bearerToken = ""
        gebruiker = null
    }
}

