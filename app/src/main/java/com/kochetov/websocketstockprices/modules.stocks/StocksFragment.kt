package com.kochetov.websocketstockprices.modules.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kochetov.websocketstockprices.R
import com.kochetov.websocketstockprices.common.Outcome
import com.kochetov.websocketstockprices.common.StocksConstants
import com.kochetov.websocketstockprices.usecases.stocks.model.Stock
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.content_stocks_loading.*

class StocksFragment : DaggerFragment() {

    private lateinit var stateConstraintLayout: ConstraintLayout

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<StocksViewModel> { viewModelFactory }

    private lateinit var stocksAdapter: StocksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stocks, container, false)
        stateConstraintLayout = view.findViewById(R.id.root_stocks_layout)
        stateConstraintLayout.loadLayoutDescription(R.xml.constraint_layout_stocks_fragment_states)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeAdapter()
        startViewModel()
    }

    private fun initializeAdapter() {
        stocksAdapter = StocksAdapter(StocksConstants.stocksMap.values.toList())
        rv_stocks_list.apply {
            setHasFixedSize(true)
            adapter = stocksAdapter
        }
    }

    private fun startViewModel() {
        StocksConstants.stocksMap.values.forEach {
            viewModel.subscribeToStock(it.isinCode)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, Observer { outcome ->
            when (outcome) {
                is Outcome.Success -> {
                    bindResultView(outcome.data)
                }

                is Outcome.Progress -> {
                    bindLoadingView(outcome.loading)
                }

                is Outcome.Failure -> {
                    bindErrorView(outcome.e)
                }
            }
        })
    }

    private fun bindResultView(data: Stock) {
        stateConstraintLayout.setState(R.id.result, 0, 0)
        if (::stocksAdapter.isInitialized) {
            stocksAdapter.updateStock(stock = data)
        }
    }

    private fun bindLoadingView(loading: Boolean) {
        if (loading) stateConstraintLayout.setState(R.id.loading, 0, 0)
    }

    private fun bindErrorView(e: Throwable) {
        stateConstraintLayout.setState(R.id.error, 0, 0)
        Toast.makeText(
            this.context?.applicationContext,
            e.localizedMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.disposables.clear()
    }
}
