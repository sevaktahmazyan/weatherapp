package com.renderforest.weatherapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renderforest.weatherapp.base.constants.LoadingState
import com.renderforest.weatherapp.base.repository.BaseRepository

open class BaseViewModel<Rep : BaseRepository<*>> constructor(var repository: Rep) : ViewModel() {

    var isRefreshing = false

    private val mutableLoading = MutableLiveData<Int>()
    val loading: LiveData<Int> = mutableLoading

    private val apiErrorMutable: SingleLiveEvent<String> = SingleLiveEvent()
    val apiError: LiveData<String> = apiErrorMutable

    override fun onCleared() {
        repository.clear()
        super.onCleared()
    }

    fun onError(t: Throwable?) {
        mutableLoading.postValue(LoadingState.HIDE_LOADING)
        t?.printStackTrace()
        apiErrorMutable.postValue(t?.message ?: "")
        //TODO handle api specific errors
    }

    fun hideLoading() {
        mutableLoading.postValue(LoadingState.HIDE_LOADING)
    }

    open fun onRefresh() {

    }
}