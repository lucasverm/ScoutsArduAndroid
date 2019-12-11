package be.ardu.scoutsardu.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://scoutsarduapinew.azurewebsites.net/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ScoutsArduApiService{
    @GET("api/Winkelwagen/WinkelwagenItems")
    fun getWinkelwagenItems(): Deferred<List<WinkelwagenItem>>
}

//singleton
object ScoutsArduApi {
    val retrofitService: ScoutsArduApiService by lazy {
        retrofit.create(ScoutsArduApiService::class.java)
    }
}

