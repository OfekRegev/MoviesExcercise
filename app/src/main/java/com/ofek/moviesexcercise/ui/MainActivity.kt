package com.ofek.moviesexcercise.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import com.ofek.moviesexcercise.ui.MainActivity.Companion.SCANNER_REQUEST_CODE
import com.ofek.moviesexcercise.ui.movie_details.MovieDetailsFragment
import com.ofek.moviesexcercise.ui.movies_list.MoviesListFragment
import com.ofek.moviesexcercise.ui.movies_list.OnItemSelectionListener
import com.ofek.moviesexcercise.ui.splash_screen.SplashFragment

class MainActivity : AppCompatActivity(), OnItemSelectionListener,
    SplashFragment.SplashFragmentCallbacks {

    companion object {
        private const val MOVIE_DETAILS_FRAG_TAG = "movie_details"
        const val SCANNER_REQUEST_CODE = 55
        private const val MOVIES_LIST_FRAG_TAG = "movie_details"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.main_layout, SplashFragment())
            .commit()
    }

    override fun onMovieSelected(uiMovie: UiMovie) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_layout, MovieDetailsFragment.newInstance(uiMovie))
            .addToBackStack(MOVIE_DETAILS_FRAG_TAG).commit()
    }

    override fun onMoviesLoaded() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_layout, MoviesListFragment(), MOVIES_LIST_FRAG_TAG).commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == SCANNER_REQUEST_CODE) {
            if (requestCode == Activity.RESULT_OK) {
                // refreshing the movies list screen
                val fragment: MoviesListFragment? =
                    supportFragmentManager.findFragmentByTag(MOVIES_LIST_FRAG_TAG) as MoviesListFragment
                fragment?.let {
                    it.refreshMoviesList()
                } ?: run {
                    supportFragmentManager.beginTransaction().replace(R.id.main_layout,MoviesListFragment(),MOVIES_LIST_FRAG_TAG).commit()
                }

            }
        }
    }

}
