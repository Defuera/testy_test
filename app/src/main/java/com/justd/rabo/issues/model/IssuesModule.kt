package com.justd.rabo.issues.model

import com.justd.rabo.app.BaseViewModel
import com.justd.rabo.issues.MainStateHolder
import com.justd.rabo.issues.MainViewModel
import com.justd.rabo.issues.ViewState
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object IssuesModule {

    val module = module {
        factory { RawResourceProvider(androidContext()) }
        factory { GetIssuesUsecase(rawProvider = get()) }
        viewModel<BaseViewModel<ViewState>> {
            MainViewModel(
                stateHolder = MainStateHolder(),
                dispatcherProvider = get(),
                getIssuesUsecase = get()
            )
        }
    }
}