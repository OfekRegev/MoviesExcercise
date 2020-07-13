package com.ofek.moviesexcercise.ui

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.GlideContext
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.cache.DiskCache
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.module.AppGlideModule
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider
import java.io.File

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalDependencyProvider.initGlobalProvider(this)

    }
}