package co.idearun.learnandroid.di

import co.idearun.common.BuildConfig.BASE_URL
import co.idearun.common.BuildConfig.X_API_KEY
import co.idearun.data.local.di.formBuilderLocalModule
import co.idearun.data.remote.di.createRemoteFormModule
import co.idearun.data.repository.di.formRepositoryModule
import co.idearun.feature.di.featureModule

val appComponent = listOf(
    createRemoteFormModule(BASE_URL, X_API_KEY),
    formBuilderLocalModule,
    formRepositoryModule,
    featureModule
)