package com.kochetov.websocketstockprices

import androidx.lifecycle.Observer
import com.kochetov.websocketstockprices.base.BaseTestClass
import com.kochetov.websocketstockprices.common.Outcome
import com.kochetov.websocketstockprices.modules.stocks.StocksViewModel
import com.kochetov.websocketstockprices.usecases.stocks.StocksUseCases
import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Flowable
import org.junit.Test

class StocksViewModelTest : BaseTestClass() {

    @MockK
    lateinit var stocksUseCases: StocksUseCases

    @MockK
    lateinit var stateObserver: Observer<Outcome<Stock>>

    @MockK
    lateinit var stock: Stock

    @MockK
    lateinit var list: List<String>

    private val viewModel: StocksViewModel by lazy {
        StocksViewModel(
            stocksUseCases
        )
    }

    @Test
    fun test_get_current_stocks_success() {
        viewModel.state.observeForever(stateObserver)

        every {
            stocksUseCases.getStocks(list)
        } returns Flowable.just(stock)

        viewModel.getStocks(list)

        verify(exactly = 1) { stocksUseCases.getStocks(list) }

        verify(exactly = 1) { stateObserver.onChanged(Outcome.success(stock)) }
    }

    @Test
    fun test_get_current_stocks_error() {
        val error = Throwable("test_get_current_stocks_error")
        viewModel.state.observeForever(stateObserver)

        every {
            stocksUseCases.getStocks(list)
        } returns Flowable.error(error)

        viewModel.getStocks(list)

        verify(exactly = 1) { stocksUseCases.getStocks(list) }

        verify(exactly = 1) { stateObserver.onChanged(Outcome.failure(error)) }
    }
}
