package com.justd.rabo.issues

import android.arch.lifecycle.ViewModel
import com.justd.rabo.app.networking.DispatcherProvider
import com.justd.rabo.issues.model.GetIssuesUsecase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainViewModel(
    val stateHolder: MainStateHolder,
    private val dispatcherProvider: DispatcherProvider,
    private val getIssuesUsecase: GetIssuesUsecase
) : ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(dispatcherProvider.main + viewModelJob)

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

}

