package com.kochetov.websocketstockprices.di.modules

import androidx.lifecycle.ViewModel
import com.kochetov.websocketstockprices.di.viewmodel.ViewModelBuilder
import com.kochetov.websocketstockprices.di.viewmodel.ViewModelKey
import com.kochetov.websocketstockprices.modules.stocks.StocksFragment
import com.kochetov.websocketstockprices.modules.stocks.StocksViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class StocksModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    abstract fun stocksFragment(): StocksFragment

    @Binds
    @IntoMap
    @ViewModelKey(StocksViewModel::class)
    abstract fun bindViewModel(viewModel: StocksViewModel): ViewModel
}
