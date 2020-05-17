package com.kochetov.websocketstockprices.usecases.stocks

import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface StocksUseCases {
    fun subscribeToStock(isinCode: String): Flowable<Stock>
}
