package com.ofek.moviesexcercise.ui.movies_list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.movies_screen.MoviesListScreenVM
import com.ofek.moviesexcercise.presentation.movies_screen.MoviesListState
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider
import com.ofek.moviesexcercise.ui.scan_movie_screen.ScanMovieScreen
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment(), Observer<MoviesListState> {

    private lateinit var viewModel : MoviesListScreenVM
    private lateinit var moviesRv : RecyclerView
    private lateinit var openScannerBtn : ImageView
    private var adapter: MoviesListAdapter? = null
    private lateinit var loadingLay: ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,GlobalDependencyProvider.provideMoviesListScreenVMFactory()).get(MoviesListScreenVM::class.java)
        viewModel.stateLiveData.observe(this,this)
        viewModel.loadMoviesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesRv = view.findViewById(R.id.movies_rv)
        moviesRv.layoutManager = LinearLayoutManager(context)
        loadingLay = view.findViewById(R.id.loading_lay_movies_list)
        openScannerBtn = view.findViewById(R.id.open_scanner_btn)
        openScannerBtn.setOnClickListener {
            startActivity(Intent(context,ScanMovieScreen::class.java))
        }
    }

    override fun onChanged(t: MoviesListState?) {
        if (t!!.loading) {
            loadingLay.visibility = View.VISIBLE
        } else {
            loadingLay.visibility = View.GONE
        }
        // in order to prevent recreating the adapter on each state change
        // on the initial state the list is empty and will not update when data loads
        // thus, initializing the adapter only if the list isn't empty
        if (adapter == null && !t.moviesList.isEmpty()) {
            // lets the parent activity handle item selection
            adapter = MoviesListAdapter(t.moviesList,activity as OnItemSelectionListener)
            moviesRv.adapter = adapter
        }

    }
}
