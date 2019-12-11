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


private const val BASE_URL = "https://scoutsarduapinew.azurewebsites.net/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ScoutsArduApiService{
    @GET("api/Winkelwagen/WinkelwagenItems")
    suspend fun getWinkelwagenItems(): List<WinkelwagenItem>

    @GET("api/Winkelwagen/stamhistoriek")
    suspend fun getStamHistory(): List<Winkelwagen>

    @POST("api/Winkelwagen")
    suspend fun postWinkelwagen(
        @Body winkelwagen: Winkelwagen
    ): Winkelwagen

}

//singleton
object ScoutsArduApi {
    val retrofitService: ScoutsArduApiService by lazy {
        retrofit.create(ScoutsArduApiService::class.java)
    }
}

