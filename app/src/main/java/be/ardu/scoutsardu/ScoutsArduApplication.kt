package be.ardu.scoutsardu

import android.app.Application
import be.ardu.scoutsardu.utilities.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ScoutsArduApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Start Koin
        startKoin{
            androidContext(this@ScoutsArduApplication)
            modules(module)
        }
    }

    companion object {
        lateinit var instance: ScoutsArduApplication
            private set
    }

}