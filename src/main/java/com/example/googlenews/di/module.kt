package com.example.googlenews.di

import androidx.room.Room
import com.example.googlenews.data.api.GoogleClientNewsBuilder
import com.example.googlenews.data.local.db.AppDataBase
import com.example.googlenews.repository.GoogleNewsRepository
import com.example.googlenews.repository.GoogleNewsRepositoryImpl
import com.example.googlenews.ui.details.DetailsViewModel
import com.example.googlenews.ui.main.MainViewModel
import com.example.googlenews.ui.settings.SettingsViewModel
import com.example.googlenews.util.PreferenceHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

val repoModule = module {
    single<GoogleNewsRepository> { GoogleNewsRepositoryImpl(get(), get()) }
    single { PreferenceHelper() }
}

val retrofitModule = module {
    single { GoogleClientNewsBuilder() }
}

val localDataModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDataBase::class.java, "GoogleNews.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDataBase>().getHistoryDao() }
}


val appModules = listOf(viewModelModule, repoModule, retrofitModule, localDataModule)

