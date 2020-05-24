package com.renderforest.weatherapp.base.repository

import com.renderforest.weatherapp.base.datasource.DataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseRepository<dataSource : DataSource> @Inject constructor(var remoteDataSource: dataSource, var localDataSource: dataSource) {

    private var compositeDisposable = CompositeDisposable()

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun clear() {
        compositeDisposable.clear()
    }
}