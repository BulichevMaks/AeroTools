package com.formgrav.aerotools.di

import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.formgrav.aerotools.data.converters.SettingsDbConvertor
import com.formgrav.aerotools.data.datasourse.ArduinoClientImpl
import com.formgrav.aerotools.data.datasourse.room.AppDatabase
import com.formgrav.aerotools.data.datasourse.room.MIGRATION_1_2
import com.formgrav.aerotools.data.repository.ArduinoRepositoryImpl
import com.formgrav.aerotools.data.repository.SettingsRepositoryImpl
import com.formgrav.aerotools.domain.api.ArduinoRepository
import com.formgrav.aerotools.domain.api.SettingsRepository
import com.formgrav.aerotools.domain.usecase.CRUDSettingsUseCase
import com.formgrav.aerotools.domain.usecase.CounterUseCase
import com.formgrav.aerotools.ui.tabfragments.GrayLineFragment
import com.formgrav.aerotools.ui.viewmodel.AirSpeedViewModel
import com.formgrav.aerotools.ui.viewmodel.GrayLineViewModel
import com.formgrav.aerotools.ui.viewmodel.GreenLineViewModel
import com.formgrav.aerotools.ui.viewmodel.RedLine2ViewModel
import com.formgrav.aerotools.ui.viewmodel.RedLineViewModel
import com.formgrav.aerotools.ui.viewmodel.SpeedViewModel
import com.formgrav.aerotools.ui.viewmodel.YellowLineViewModel
import org.koin.android.ext.koin.androidApplication

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {


    viewModel {
        SpeedViewModel(get())
    }
    viewModel {
        AirSpeedViewModel(get())
    }
    viewModel {
        GrayLineViewModel(get())
    }
    viewModel {
        GreenLineViewModel(get())
    }
    viewModel {
        YellowLineViewModel(get())
    }
    viewModel {
        RedLineViewModel(get())
    }
    viewModel {
        RedLine2ViewModel(get())
    }

    single {
        Handler(Looper.getMainLooper())
    }


    single {
        ArduinoClientImpl( get(), androidContext())
    }



    single<ArduinoRepository> {
        ArduinoRepositoryImpl(get())
    }

    single {
        CounterUseCase(get())
    }

    single {
        GrayLineFragment()
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    factory { SettingsDbConvertor() }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get())
    }

    single {
        CRUDSettingsUseCase(get())
    }
}