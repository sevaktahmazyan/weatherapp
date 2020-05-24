package com.renderforest.weatherapp.base.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.base.activity.BaseActivity
import com.renderforest.weatherapp.base.model.BaseResponse
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_base.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutResId(): Int

    var displayWidth = 0
    var displayHeight = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (canRefresh()) {
            val view = inflater.inflate(R.layout.fragment_base, container, false)
            if (getLayoutResId() != 0) {
                view.container_view.addView(LayoutInflater.from(context).inflate(getLayoutResId(), null, false))
            }
            return view
        }
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayWidth = resources.displayMetrics.widthPixels
        displayHeight = resources.displayMetrics.heightPixels
        swipe_to_refresh?.apply {
            isEnabled = canRefresh()
            setOnRefreshListener { onRefresh() }
        }
        if (activity is BaseActivity) {
            getBaseActivity()?.setStatusBarColor(getStatusBarColor())
        }
    }

    @Subscribe
    open fun defaultSubscription(model: BaseResponse) {

    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    open fun setRefreshing(isRefreshing: Boolean) {
        swipe_to_refresh?.isRefreshing = isRefreshing
    }

    open fun onRefresh() {
    }

    open fun canRefresh(): Boolean {
        return false
    }

    open fun onBackPressed() {
        getBaseActivity()?.closeKeyboard()
    }

    protected open fun getBaseActivity(): BaseActivity? {
        return activity as BaseActivity?
    }

    protected open fun getStatusBarColor(): Int {
        return R.color.colorAccent
    }

    fun navigate(@IdRes resId: Int, @Nullable args: Bundle? = null) {
        getBaseActivity()?.navigate(resId, args)
    }

    fun popBackStack(destId: Int = -1, inclusive: Boolean = true) {
        getBaseActivity()?.popBackStack(destId, inclusive)
    }

    fun toast(text: String) {
        getBaseActivity()?.toast(text)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    protected open fun pixelOf(resId: Int): Int {
        return context?.resources?.getDimensionPixelOffset(resId) ?: 0
    }
}