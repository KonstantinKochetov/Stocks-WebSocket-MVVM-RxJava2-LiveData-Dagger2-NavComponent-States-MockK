package com.kochetov.websocketstockprices.di.modules

import com.google.gson.Gson
import com.kochetov.websocketstockprices.usecases.stocks.StocksUseCases
import com.kochetov.websocketstockprices.usecases.stocks.StocksUseCasesImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun providesStocksUseCases(gson: Gson, okHttpClient: OkHttpClient): StocksUseCases =
        StocksUseCasesImpl(gson = gson, okHttpClient = okHttpClient)
}
