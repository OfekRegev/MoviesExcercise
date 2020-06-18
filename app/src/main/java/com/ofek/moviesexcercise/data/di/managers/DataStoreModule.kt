package com.ofek.moviesexcercise.data.di.managers

import com.ofek.moviesexcercise.domain.di.UseCasesModule

/**
 * see [UseCasesModule] for explanation about the module classes
 */
object DataStoreModule {
    private var managersProvider: DataStoresProvider? = null
    fun injectProvider(provider: DataStoresProvider?) {
        managersProvider = provider
    }

    fun getManagersProvider(): DataStoresProvider {
        if (managersProvider == null) {
            injectProvider(DataStoresProviderImp())
        }
        return managersProvider!!
    }
}