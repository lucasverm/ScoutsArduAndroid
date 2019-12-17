package be.ardu.scoutsardu.network

import retrofit2.http.*


public const val BASE_URL = "https://scoutsarduapinew.azurewebsites.net/"

interface ScoutsArduApiService {
    @GET("api/Winkelwagen/WinkelwagenItems")
    suspend fun getWinkelwagenItems(): List<WinkelwagenItem>

    @GET("api/Winkelwagen/stamhistoriek")
    suspend fun getStamHistory(@Header("Authorization") bearerToken: String): ArrayList<Winkelwagen>

    @GET("api/Winkelwagen/winkelwagens")
    suspend fun getMijnHistory(@Header("Authorization") bearerToken: String): ArrayList<Winkelwagen>

    @POST("api/Winkelwagen")
    suspend fun postWinkelwagen(
        @Body winkelwagen: Winkelwagen, @Header("Authorization") bearerToken: String
    ): Winkelwagen

    @POST("api/Account/login")
    suspend fun login(
        @Body data: SendLoginData
    ): String

    @POST("api/Account/register")
    suspend fun registreer(
        @Body data: SendRegistreerData
    ): String

    @GET("api/Account")
    suspend fun getGebruiker(@Header("Authorization") bearerToken: String): Gebruiker


    @PUT("api/Account")
    suspend fun putGebruiker(
        @Body data: PutGebruikerData, @Header("Authorization") bearerToken: String
    ): Gebruiker
}

//singleton


enum class ScoutsArduApiStatus { LOADING, ERROR, DONE, DEFAULT }

