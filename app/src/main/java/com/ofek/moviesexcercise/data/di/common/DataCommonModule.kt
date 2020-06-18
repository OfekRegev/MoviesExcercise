package com.ofek.moviesexcercise.data.di.common

import com.ofek.moviesexcercise.domain.di.UseCasesModule

/**
 * see [UseCasesModule] for explanation about the module classes
 */
object DataCommonModule {
    private var managersProvider: DataCommonProvider? = null
    fun injectProvider(provider: DataCommonProvider?) {
        managersProvider = provider
    }

    fun dataCommonProvider(): DataCommonProvider {
        if (managersProvider == null) {
            injectProvider(DataCommonProviderImp())
        }
        return managersProvider!!
    }
}