package com.justd.rabo.issues

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.justd.rabo.app.BaseViewModel
import com.justd.rabo.app.networking.DispatcherProvider
import com.justd.rabo.issues.model.GetIssuesUsecase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainViewModel(
    val stateHolder: MainStateHolder,
    override val dispatcherProvider: DispatcherProvider,
    private val getIssuesUsecase: GetIssuesUsecase
) : BaseViewModel<ViewState>() {

    init {
        loadData()
    }

    private fun loadData() {
        uiScope.launch(dispatcherProvider.main) {
            try {
                val result = withContext(dispatcherProvider.io) { getIssuesUsecase.execute() }
                stateHolder.showItems(result)
            } catch (exception: IOException) {
                stateHolder.showError()
            }

        }
    }

    override fun observeState(lifecycleOwner: LifecycleOwner, observer: Observer<ViewState>) {
        stateHolder.liveData.observe(lifecycleOwner, observer)
    }

}

