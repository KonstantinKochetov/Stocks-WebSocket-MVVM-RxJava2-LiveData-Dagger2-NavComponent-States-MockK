package com.kochetov.websocketstockprices.modules.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kochetov.websocketstockprices.common.Outcome
import com.kochetov.websocketstockprices.usecases.stocks.StocksUseCases
import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StocksViewModel @Inject constructor(
    private val stocksUseCases: StocksUseCases
) : ViewModel() {

    val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    private val _state = MutableLiveData<Outcome<Stock>>()
    val state: LiveData<Outcome<Stock>> = _state

    fun subscribeToStock(isinCode: String) {
        stocksUseCases.subscribeToStock(isinCode = isinCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.postValue(Outcome.success(it))
            }, { error ->
                error?.let {
                    _state.postValue(Outcome.failure(it))
                }
            }).addTo(disposables)
    }
}
