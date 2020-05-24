package com.renderforest.weatherapp.base.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.renderforest.weatherapp.base.BaseViewModel
import com.renderforest.weatherapp.base.constants.LoadingState
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject


abstract class BaseActivityWithViewModel<V : BaseViewModel<*>> : BaseActivity() {

    lateinit var vm: V

    abstract fun getViewModelClass(): Class<V>

    private var loadingCurrentState: Int = 0

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProviders.of(this, vmFactory).get(getViewModelClass())

        vm.loading.observe(this, Observer<Int> { t -> updateLoadingState(t) })

        vm.apiError.observe(this, Observer<String> { message ->
            showErrorMessages(message)
        })
    }

    fun showErrorMessages(message: String) {
        showMessageDialog(message)
    }

    fun updateLoadingState(loadingState: Int) {
        if (loadingState == loadingCurrentState) {
            return
        }

        when (loadingState) {
            LoadingState.SHOW_LOADING, LoadingState.KEEP_LOADING -> {
                loading_view.visibility = View.VISIBLE
            }
            LoadingState.HIDE_LOADING -> {
                loading_view.visibility = View.GONE
            }
        }

        loadingCurrentState = loadingState
    }
}