package com.kochetov.websocketstockprices.di.components

import android.content.Context
import com.kochetov.websocketstockprices.App
import com.kochetov.websocketstockprices.di.modules.NetworkModule
import com.kochetov.websocketstockprices.di.modules.StocksModule
import com.kochetov.websocketstockprices.di.modules.UseCasesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        StocksModule::class,
        NetworkModule::class,
        UseCasesModule::class]
)
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
