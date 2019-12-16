package be.ardu.scoutsardu.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://scoutsarduapinew.azurewebsites.net/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

var gson = GsonBuilder()
    .setLenient()
    .create()!!


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

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
object ScoutsArduApi {
    val retrofitService: ScoutsArduApiService by lazy {
        retrofit.create(ScoutsArduApiService::class.java)
    }
}

enum class ScoutsArduApiStatus { LOADING, ERROR, DONE, DEFAULT }

