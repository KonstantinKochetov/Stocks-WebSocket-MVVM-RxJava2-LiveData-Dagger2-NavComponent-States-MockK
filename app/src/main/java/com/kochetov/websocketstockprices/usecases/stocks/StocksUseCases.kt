package com.kochetov.websocketstockprices.usecases.stocks

import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import io.reactivex.Flowable

interface StocksUseCases {
    fun subscribeToStocks(codes: List<String>): Flowable<Stock>
}
