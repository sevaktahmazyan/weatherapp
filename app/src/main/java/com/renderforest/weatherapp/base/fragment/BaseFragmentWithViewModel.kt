package com.renderforest.weatherapp.base.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.renderforest.weatherapp.base.BaseViewModel
import com.renderforest.weatherapp.base.activity.BaseActivityWithViewModel
import javax.inject.Inject

abstract class BaseFragmentWithViewModel<V : BaseViewModel<*>> : BaseFragment() {

    protected lateinit var vm: V

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    protected abstract fun getViewModelClass(): Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = if (getViewModelOwner() == null) {
            ViewModelProviders.of(this, vmFactory).get(getViewModelClass())
        } else {
            ViewModelProviders.of(getViewModelOwner()!!, vmFactory).get(getViewModelClass())
        }

        vm.loading.observe(this, Observer<Int> { t -> getBaseActivityWitViewModel()?.updateLoadingState(t) })
        vm.apiError.observe(this, Observer<String> { message ->
            tryHideLoadingIfExist()
            getBaseActivityWitViewModel()?.showErrorMessages(message)
        })
    }

    override fun setRefreshing(isRefreshing: Boolean) {
        super.setRefreshing(isRefreshing)
        vm.isRefreshing = isRefreshing
    }

    override fun onRefresh() {
        vm.isRefreshing = true
        vm.onRefresh()
    }

    override fun canRefresh(): Boolean {
        return false
    }

    private fun tryHideLoadingIfExist() {
        setRefreshing(false)
    }

    protected open fun getBaseActivityWitViewModel(): BaseActivityWithViewModel<*>? {
        return activity as BaseActivityWithViewModel<*>?
    }

    open fun getViewModelOwner(): AppCompatActivity? {
        return null
    }
}