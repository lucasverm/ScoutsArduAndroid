package be.ardu.scoutsardu.network

import android.view.animation.ScaleAnimation
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.util.*

private const val BASE_URL = "http://scoutsarduapi.azurewebsites.net/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ScoutsArduApiService{
    @GET("api/Winkelwagen/WinkelwagenItems")
    fun getWinkelwagenItems(): Call<String>
}

//singleton
object ScoutsArduApi {
    val retrofitService: ScoutsArduApiService by lazy {
        retrofit.create(ScoutsArduApiService::class.java)
    }
}

