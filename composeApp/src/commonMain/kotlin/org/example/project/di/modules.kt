package org.example.project.di

import org.example.project.getPlatformEngine
import org.example.project.network.ApiClient
import org.example.project.network.provideHttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.example.project.presentation.viewmodel.*

expect val platformModule: Module

val sharedModule = module {
    single { provideHttpClient(getPlatformEngine()) }
    single { ApiClient(get()) }

    //singleOf(::ShoppingRepoImp).binds(ShoppingRepo::class)
    viewModelOf(::ProductViewModel)
    viewModelOf(::LoginViewModel) // OR viewModel { LoginViewModel(get()) }

}