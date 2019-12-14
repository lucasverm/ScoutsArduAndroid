package be.ardu.scoutsardu.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import com.squareup.moshi.Types.newParameterizedType
import okhttp3.ResponseBody
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.google.gson.GsonBuilder
import com.google.gson.Gson




private const val BASE_URL = "https://scoutsarduapinew.azurewebsites.net/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

var gson = GsonBuilder()
    .setLenient()
    .create()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ScoutsArduApiService{
    @GET("api/Winkelwagen/WinkelwagenItems")
    fun getWinkelwagenItems(): Deferred<List<WinkelwagenItem>>

    @GET("api/Winkelwagen/stamhistoriek")
    fun getStamHistory(@Header("Authorization") bearerToken: String): Deferred<ArrayList<Winkelwagen>>

    @GET("api/Winkelwagen/winkelwagens")
    fun getMijnHistory(@Header("Authorization") bearerToken: String): Deferred<ArrayList<Winkelwagen>>

    @POST("api/Winkelwagen")
    fun postWinkelwagen(
        @Body winkelwagen: Winkelwagen, @Header("Authorization") bearerToken: String
    ): Deferred<Winkelwagen>

    @POST("api/Account/login")
    suspend fun login(
        @Body data: SendLoginData
    ): String

    @GET("api/Account")
    suspend fun getGebruiker(@Header("Authorization") bearerToken: String): Gebruiker

}

//singleton
object ScoutsArduApi {
    val retrofitService: ScoutsArduApiService by lazy {
        retrofit.create(ScoutsArduApiService::class.java)
    }
}

enum class ScoutsArduApiStatus { LOADING, ERROR, DONE, DEFAULT }

