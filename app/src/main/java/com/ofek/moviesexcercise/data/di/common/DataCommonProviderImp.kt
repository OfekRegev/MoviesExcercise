package com.ofek.moviesexcercise.data.di.common

import android.content.Context
import androidx.room.Room
import com.ofek.moviesexcercise.data.common.Constants
import com.ofek.moviesexcercise.data.movies.datastores.MoviesService
import com.ofek.moviesexcercise.data.movies.datastores.roomdb.MoviesDao
import com.ofek.moviesexcercise.data.movies.datastores.roomdb.MoviesDb
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DataCommonProviderImp : DataCommonProvider {
    override fun provideMoviesService(): MoviesService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.MOVIES_API_BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .build()
            .create(MoviesService::class.java)
    }

    override fun provideMoviesDao(moviesDb: MoviesDb): MoviesDao {
        return moviesDb.moviesDao();
    }

    override fun provideMoviesDb(context: Context): MoviesDb {
        return Room.databaseBuilder(context,MoviesDb::class.java,"movies.db").build()
    }

}