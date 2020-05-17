package com.kochetov.websocketstockprices.usecases.stocks

import android.util.Log
import com.google.gson.Gson
import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class StocksUseCasesImpl(private val gson: Gson, okHttpClient: OkHttpClient) : StocksUseCases,
    WebSocketListener() {

    private companion object {
        const val URL = "ws://159.89.15.214:8080/"
        const val SUBSCRIBE = "{\"subscribe\": \"{%s}\"}"
    }

    private var webSocket: WebSocket

    init {
        val request = Request.Builder()
            .url(URL)
            .build()
        webSocket = okHttpClient.newWebSocket(request, this)
    }


    private val publishSubjects: MutableMap<String, PublishProcessor<Stock>> = mutableMapOf()

    override fun subscribeToStock(isinCode: String): Flowable<Stock> {
        val publishProcessor = PublishProcessor.create<Stock>()
        publishSubjects[isinCode] = publishProcessor
        webSocket.send(SUBSCRIBE.format(isinCode))
        return publishProcessor.onBackpressureLatest()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val stock = gson.fromJson(text, Stock::class.java)
        stock.isinCode = stock.isinCode.substring(1, stock.isinCode.length - 1)
        Log.d("kok", "$stock")
        publishSubjects[stock.isinCode]?.onNext(stock)
    }

}
