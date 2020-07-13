package com.ofek.moviesexcercise.ui.favorites_screen

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.favorite_screen.FavoritesView
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider
import com.ofek.moviesexcercise.ui.movies_list.MoviesListAdapter
import com.ofek.moviesexcercise.ui.movies_list.OnItemSelectionListener


class FavoritesFragment : Fragment(),FavoritesView{

    private val presenter  = GlobalDependencyProvider.provideFavoritesScreenPresenter()
    private lateinit var  favMoviesRv: RecyclerView
    private lateinit var emptyFavLay: ViewGroup
    private lateinit var loadingLay: ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearResources()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingLay = view.findViewById(R.id.fav_loading_lay)
        emptyFavLay = view.findViewById(R.id.fav_empty)
        favMoviesRv = view.findViewById(R.id.favorites_rv)
        favMoviesRv.layoutManager =  GridLayoutManager(view.context,2)
        presenter.loadMovies()
    }

    override fun onFavoriteMoviesLoaded(favoriteMovies: List<UiMovie>) {
        // show the view containing the list
        loadingLay.visibility = View.GONE
        emptyFavLay.visibility = View.GONE
        favMoviesRv.adapter = MoviesListAdapter(ArrayList(favoriteMovies),activity as OnItemSelectionListener)
    }

    override fun onStartLoadingMovies() {
        loadingLay.visibility = View.VISIBLE
        emptyFavLay.visibility = View.GONE
    }

    override fun onMoviesFailedToLoad() {
        loadingLay.visibility = View.GONE
        emptyFavLay.visibility = View.GONE
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage("Favorites movies failed to load, please check your internet connection and try again.")
            .setNegativeButton("Retry") { dialog, _ ->
                presenter.loadMovies()
                dialog.dismiss()
            }.setCancelable(false)
            .create().show()
    }

    override fun noFavoriteMoviesFound() {
        loadingLay.visibility = View.GONE
        emptyFavLay.visibility = View.VISIBLE
    }

    fun reloadFavorites() {
        presenter.loadMovies()
    }
}
