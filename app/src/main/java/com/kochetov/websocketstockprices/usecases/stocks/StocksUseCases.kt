package com.kochetov.websocketstockprices.usecases.stocks

import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import io.reactivex.Flowable

interface StocksUseCases {
    fun getStocks(codes: List<String>): Flowable<Stock>
}
