package com.formgrav.aerotools.di

import com.formgrav.aerotools.data.datasourse.ArduinoClientImpl
import com.formgrav.aerotools.data.repository.ArduinoRepositoryImpl
import com.formgrav.aerotools.domain.api.ArduinoRepository
import com.formgrav.aerotools.domain.usecase.CounterUseCase
import com.formgrav.aerotools.ui.tabfragments.GrayLineFragment
import com.formgrav.aerotools.ui.viewmodel.SpeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {


    viewModel {
        SpeedViewModel(get())
    }

    single {
        ArduinoClientImpl(androidContext())
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
}