package com.ofek.moviesexcercise.domain.di;


/**
 * the class which  holds the provider instance and responsible for the dependency injection
 * changing the provider instance by the "injectProvider" method allows control on the dependency injection by the provider
 * different providers will be used for different purposes(i.e test provider which provides mocks)
 */
object UseCasesModule {

    private var useCasesProvider: UseCasesProvider? = null

    /**
     * this method allows injection of costume provider such as test provider
     * @param provider
     */
    fun injectProvider(provider: UseCasesProvider?) {
        useCasesProvider = provider
    }

    /**
     * returns the current use cases provider, if use cases provider not already injected the method injects a default provider
     * @return [UseCasesProvider] instance
     */
    fun getUseCasesProvider(): UseCasesProvider {
        if (useCasesProvider == null) {
            injectProvider(UseCasesProviderImp())
        }
        return useCasesProvider!!
    }
}
