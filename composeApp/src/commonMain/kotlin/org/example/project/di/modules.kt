package org.example.project.di

import com.example.project.AnandMartDb
import com.russhwolf.settings.Settings
import org.example.project.config.AppConfig
import org.example.project.config.AppConfigImpl
import org.example.project.domain.repository.*
import org.example.project.getPlatformEngine
import org.example.project.network.ApiClient
import org.example.project.network.provideHttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.example.project.presentation.viewmodel.*
import org.example.project.data.repository.*
import org.example.project.utils.DatabaseDriverFactory
import org.koin.dsl.binds


expect val platformModule: Module

val sharedModule = module {
    single { provideHttpClient(getPlatformEngine()) }
    single { ApiClient(get()) }

    single { AnandMartDb(get<DatabaseDriverFactory>().createDriver()) }

    singleOf(::AppConfigImpl).binds(arrayOf(AppConfig::class))

    singleOf(::LoginRepositoryImpl).binds(arrayOf(LoginRepository::class))
    singleOf(::HomeRepositoryImpl).binds(arrayOf(HomeRepository::class))
    singleOf(::SessionRepoImpl).binds(arrayOf(SessionRepository::class))
    viewModelOf(::ProductViewModel)
    viewModelOf(::LoginViewModel) // OR viewModel { LoginViewModel(get()) }
    viewModelOf(::ProfileViewModel)

}