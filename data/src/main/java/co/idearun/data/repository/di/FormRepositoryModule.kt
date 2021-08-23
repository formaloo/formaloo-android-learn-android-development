package co.idearun.data.repository.di

import co.idearun.data.remote.FormDatasource
import co.idearun.data.repository.FormzRepo
import co.idearun.data.repository.sharedRepo.SharedRepository
import co.idearun.data.repository.sharedRepo.SharedRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val formRepositoryModule = module {
    single<FormzRepo>(named("FormzRepo")) {
        FormzRepo(get<FormDatasource>(named("FormzDatasource")),get(),get(),get())
    }

    factory(named("SharedRepositoryImpl")) {
        SharedRepositoryImpl(get()) as SharedRepository
    }
}