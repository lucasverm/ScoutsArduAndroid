package be.ardu.scoutsardu.utilities

import be.ardu.scoutsardu.database.ScoutsArduDatabase
import be.ardu.scoutsardu.network.BASE_URL
import be.ardu.scoutsardu.network.ScoutsArduApiService
import be.ardu.scoutsardu.repositories.AccountRepository
import be.ardu.scoutsardu.repositories.HistoriekDatabaseRepository
import be.ardu.scoutsardu.repositories.WinkelwagenRepository
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val module = module {

    //Retrofit
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build() as Retrofit
    }

    single { provideApiServe(get()) }


    single {
        GsonBuilder()
            .setLenient()
            .create()!!
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    }

    single { ScoutsArduDatabase.getInstance(get()).mijnHistoriek }
    single { AccountRepository() }
    single { HistoriekDatabaseRepository() }
    single { WinkelwagenRepository() }


}

private fun provideApiServe(retrofit: Retrofit): ScoutsArduApiService {
    return retrofit.create(ScoutsArduApiService::class.java)
}