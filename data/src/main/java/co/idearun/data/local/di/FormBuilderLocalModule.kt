package co.idearun.data.local.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import co.idearun.data.local.FormBuilderDB
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val formBuilderLocalModule = module {
    single() { FormBuilderDB.buildDatabase(androidContext()) }
    factory { (get() as FormBuilderDB).formDao() }
    factory { (get() as FormBuilderDB).formKeysDao() }
    factory { (get() as FormBuilderDB).submitDao() }
    single {
        provideSharePreferences(androidApplication())
    }
}
private fun provideSharePreferences(app: Application): SharedPreferences =
    app.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)