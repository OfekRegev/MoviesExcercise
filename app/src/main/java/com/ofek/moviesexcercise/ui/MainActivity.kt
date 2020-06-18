package com.ofek.moviesexcercise.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import com.ofek.moviesexcercise.ui.movie_details.MovieDetailsFragment
import com.ofek.moviesexcercise.ui.movies_list.MoviesListFragment
import com.ofek.moviesexcercise.ui.movies_list.OnItemSelectionListener
import com.ofek.moviesexcercise.ui.splash_screen.SplashFragment

class MainActivity : AppCompatActivity(), OnItemSelectionListener, SplashFragment.SplashFragmentCallbacks{

    companion object{
        private const val MOVIE_DETAILS_FRAG_TAG: String = "movie_details"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.main_layout,SplashFragment()).commit()
    }

    override fun onMovieSelected(uiMovie: UiMovie) {
        supportFragmentManager.beginTransaction().add(R.id.main_layout,MovieDetailsFragment.newInstance(uiMovie)).addToBackStack(MOVIE_DETAILS_FRAG_TAG).commit()
    }

    override fun onMoviesLoaded() {
        supportFragmentManager.beginTransaction().replace(R.id.main_layout,MoviesListFragment()).commit()
    }
}
