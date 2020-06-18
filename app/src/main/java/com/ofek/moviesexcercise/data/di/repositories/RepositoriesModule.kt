package com.ofek.moviesexcercise.data.di.repositories

import com.ofek.moviesexcercise.domain.di.UseCasesModule

/**
 * see [UseCasesModule] for explanation about the module classes
 */
object RepositoriesModule {
    private var repositoriesProvider: RepositoriesProvider? = null
    fun injectProvider(provider: RepositoriesProvider?) {
        repositoriesProvider = provider
    }

    fun getRepositoriesProvider(): RepositoriesProvider {
        if (repositoriesProvider == null) {
            injectProvider(RepositoriesProviderImp())
        }
        return repositoriesProvider!!
    }
}