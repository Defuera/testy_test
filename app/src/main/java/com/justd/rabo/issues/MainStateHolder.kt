package com.justd.rabo.issues

import android.arch.lifecycle.MutableLiveData
import com.justd.rabo.app.model.Issue

class MainStateHolder {

    val liveData = MutableLiveData<ViewState>()

    fun showItems(items: List<Issue>) {
        liveData.postValue(ViewState.Data(items))
    }

    fun showError() {
        liveData.postValue(ViewState.Error)
    }

    fun showLoading() {
        liveData.postValue(ViewState.Loading)
    }

}

sealed class ViewState {

    object Loading : ViewState()
    object Empty : ViewState()
    object Error : ViewState()
    data class Data(val items: List<Issue>) : ViewState()

}
