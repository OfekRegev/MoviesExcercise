package com.ofek.moviesexcercise.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import com.ofek.moviesexcercise.ui.movie_details.MovieDetailsFragment
import com.ofek.moviesexcercise.ui.movies_list.MoviesListFragment
import com.ofek.moviesexcercise.ui.movies_list.OnItemSelectionListener
import com.ofek.moviesexcercise.ui.favorites_screen.FavoritesFragment
import java.util.*

class MainActivity : AppCompatActivity(), OnItemSelectionListener,MoviesListFragment.InteractionListener{

    companion object{
        private const val MOVIE_DETAILS_FRAG_TAG: String = "movie_details"
        private const val FAVORITES_FRAG_TAG: String = "favorites"
        private const val MOVIES_LIST_FRAG_TAG: String = "movies_list"
    }

    /**
     * when using FragmentTransaction.add the new fragment will be on top of the last fragment.
     * because it's possible to have 3 fragment overlay(movies_list->favorite_movies->movie_details) I'm implementing costume backstack
     * that uses FragmentTransaction.replace instead and prevent fragments overlaying
     * overlaying is bad for performance and should be avoided as much as possible
     */
    private val fragmentsStack = Stack<String>()
    private val favoritesFragment = FavoritesFragment()
    private val moviesListFrag = MoviesListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.main_layout,moviesListFrag).runOnCommit {
            fragmentsStack.add(MOVIES_LIST_FRAG_TAG)
        }.commit()
    }

    override fun onMovieSelected(uiMovie: UiMovie) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_layout,MovieDetailsFragment.newInstance(uiMovie))
            .runOnCommit {
                fragmentsStack.add(MOVIE_DETAILS_FRAG_TAG)
            }.commit()
    }

    override fun openFavoritesScreen() {
        supportFragmentManager.beginTransaction().replace(R.id.main_layout,favoritesFragment)
            .setCustomAnimations(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
            .runOnCommit {
                fragmentsStack.add(FAVORITES_FRAG_TAG)
            }
            .commit()

    }

    override fun onBackPressed() {
        // the first fragment is movies list which is the base fragment and will always be last in the stack
        if (fragmentsStack.size<=1) {
            super.onBackPressed()
            return
        }
        fragmentsStack.pop()
        if (fragmentsStack.peek() == FAVORITES_FRAG_TAG) {
            supportFragmentManager.beginTransaction().replace(R.id.main_layout,favoritesFragment)
                .setCustomAnimations(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit).commit()
            return
        }
        if (fragmentsStack.peek() == MOVIES_LIST_FRAG_TAG) {
            supportFragmentManager.beginTransaction().replace(R.id.main_layout,moviesListFrag).commit()
        }
    }
}
