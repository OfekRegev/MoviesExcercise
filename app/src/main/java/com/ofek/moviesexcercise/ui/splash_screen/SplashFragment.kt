package com.ofek.moviesexcercise.ui.splash_screen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.errors.GenericResponseError
import com.ofek.moviesexcercise.presentation.splash_screen.SplashView
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider


class SplashFragment : Fragment(),SplashView {

    private val presenter  = GlobalDependencyProvider.provideSplashScreenPresenter()
    lateinit var listener : SplashFragmentCallbacks
    lateinit var loadingLay : ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        listener = activity as SplashFragmentCallbacks
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingLay = view.findViewById(R.id.splash_loading_lay)
        presenter.loadMovies()
    }

    override fun onMoviesLoaded() {
        // lets the parent activity to handle the callback
        listener.onMoviesLoaded()
    }

    override fun onStartLoadingMovies() {
        loadingLay.visibility = View.VISIBLE
    }

    override fun onMoviesFailedToLoad(genericResponseError: GenericResponseError) {
        loadingLay.visibility = View.INVISIBLE
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage("Movies Database Failed to load, please check your internet connection and try again.")
            .setNegativeButton("Retry") { dialog, _ ->
                presenter.loadMovies()
                dialog.dismiss()
            }.setCancelable(false)
            .create().show()
    }

    interface SplashFragmentCallbacks {
        fun onMoviesLoaded();
    }

}
