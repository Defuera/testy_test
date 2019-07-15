package com.justd.rabo.issues.model

import com.justd.rabo.issues.MainStateHolder
import com.justd.rabo.issues.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object IssuesModule {

    val module = module {
        factory { RawResourceProvider(androidContext()) }
        factory { GetIssuesUsecase(rawProvider = get()) }
        viewModel {
            MainViewModel(
                stateHolder = MainStateHolder(),
                dispatcherProvider = get(),
                getIssuesUsecase = get()
            )
        }
    }
}