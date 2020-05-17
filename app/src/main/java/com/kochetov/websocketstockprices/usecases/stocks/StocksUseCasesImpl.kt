package com.kochetov.websocketstockprices.usecases.stocks

import com.google.gson.Gson
import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

// This is a simple OkHttp implementation which does not resubscribe when encountering network socket errors -> use Scarlet (https://github.com/Tinder/Scarlet) for that. It is like retrofit and also allow us to have much cleaner code and a separate Api class
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

    lateinit var publishProcessor: PublishProcessor<Stock>

    override fun getStocks(codes: List<String>): Flowable<Stock> {
        publishProcessor = PublishProcessor.create()
        codes.forEach {
            webSocket.send(SUBSCRIBE.format(it))
        }
        return publishProcessor.onBackpressureLatest()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val stock = gson.fromJson(text, Stock::class.java)
        stock.isinCode = stock.isinCode.substring(1, stock.isinCode.length - 1)
        publishProcessor.onNext(stock)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        if (::publishProcessor.isInitialized) publishProcessor.onError(t) // handles no network error on launch because publish processor is not initialized
    }
}
