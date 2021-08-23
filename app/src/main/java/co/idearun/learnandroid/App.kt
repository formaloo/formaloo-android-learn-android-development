package co.idearun.learnandroid

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import co.idearun.learnandroid.di.appComponent
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

open class App : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        configureDi()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
        }

        AppCenter.start(
            this, "657491c6-00b7-478b-985c-d1f6c86981e0",
            Analytics::class.java, Crashes::class.java
        )
    }

    //     CONFIGURATION ---
    open fun configureDi() =
        startKoin {
            androidContext(this@App)
            // your modules
            modules(provideComponent())

        }

    //     PUBLIC API ---
    open fun provideComponent() = appComponent

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.VERBOSE)
            .build()
}