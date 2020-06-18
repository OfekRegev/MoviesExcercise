package com.ofek.moviesexcercise.ui

import android.app.Application
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalDependencyProvider.initGlobalProvider(this)
    }
}