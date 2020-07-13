package com.ofek.moviesexcercise.ui.movies_list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.movies_screen.MoviesListScreenVM
import com.ofek.moviesexcercise.presentation.movies_screen.MoviesListState
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider

/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment(), Observer<MoviesListState> {

    private lateinit var viewModel: MoviesListScreenVM
    private lateinit var moviesRv: RecyclerView
    private lateinit var adapter: MoviesListAdapter
    private lateinit var loadingLay: ViewGroup
    private lateinit var favoritesBtn: ImageView
    private lateinit var listener : InteractionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        retainInstance = true
        listener = context as InteractionListener
        viewModel = ViewModelProvider(
            context as FragmentActivity,
            GlobalDependencyProvider.provideMoviesListScreenVMFactory()
        ).get(MoviesListScreenVM::class.java)
        viewModel.stateLiveData.observe(this, this)
        viewModel.loadMoviesList(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesBtn = view.findViewById(R.id.favorites_btn)
        favoritesBtn.setOnClickListener {
            listener.openFavoritesScreen()
        }
        moviesRv = view.findViewById(R.id.movies_rv)
        moviesRv.layoutManager = GridLayoutManager(view.context, 2)
        adapter = MoviesListAdapter(ArrayList(), activity as OnItemSelectionListener)
        moviesRv.adapter = adapter
        loadingLay = view.findViewById(R.id.loading_lay_movies_list)
    }

    override fun onChanged(t: MoviesListState?) {
        // state is maintained as non-null in the VM so it's safe to extract it explicitly
        if (t!!.loading) {
            loadingLay.visibility = View.VISIBLE
        } else {
            loadingLay.visibility = View.GONE
            adapter.moviesList.clear()
            adapter.moviesList.addAll(t.moviesList)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {

        super.onDestroy()
    }
    interface InteractionListener {
        fun openFavoritesScreen()
    }
}
