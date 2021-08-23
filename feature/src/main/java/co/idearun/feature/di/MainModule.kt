package co.idearun.feature.di

import co.idearun.feature.viewmodel.FormViewModel
import co.idearun.feature.viewmodel.SharedViewModel
import co.idearun.feature.viewmodel.UIViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureModule = module {
    viewModel { FormViewModel(get(named("FormzRepo"))) }
    viewModel { UIViewModel(get(named("FormzRepo"))) }
    viewModel { SharedViewModel(get(named("SharedRepositoryImpl"))) }

}
