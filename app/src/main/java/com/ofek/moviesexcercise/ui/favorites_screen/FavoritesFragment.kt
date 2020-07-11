package com.ofek.moviesexcercise.ui.favorites_screen

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewSwitcher

import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.domain.objects.MovieObj
import com.ofek.moviesexcercise.presentation.favorite_screen.FavoritesView
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider


class FavoritesFragment : Fragment(),FavoritesView {

    private val presenter  = GlobalDependencyProvider.provideSplashScreenPresenter()
    lateinit var viewSwitcher : ViewSwitcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
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
        viewSwitcher = view.findViewById(R.id.favorites_view_switcher)
        presenter.loadMovies()
    }

    override fun onFavoriteMoviesLoaded(favoriteMovies: List<MovieObj>) {
        // show the view containing the list
        viewSwitcher.nextView

    }

    override fun onStartLoadingMovies() {
        viewSwitcher.visibility = View.VISIBLE
    }

    override fun onMoviesFailedToLoad() {
        viewSwitcher.visibility = View.INVISIBLE
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage("Movies Database Failed to load, please check your internet connection and try again.")
            .setNegativeButton("Retry") { dialog, _ ->
                presenter.loadMovies()
                dialog.dismiss()
            }.setCancelable(false)
            .create().show()
    }

    override fun noFavoriteMoviesFound() {
        TODO("Not yet implemented")
    }
}
