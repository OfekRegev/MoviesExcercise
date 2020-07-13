package com.ofek.moviesexcercise.ui.movies_list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.errors.FailedToLoadFirstPageError
import com.ofek.moviesexcercise.presentation.errors.FailedToLoadMoreError
import com.ofek.moviesexcercise.presentation.errors.PresentationError
import com.ofek.moviesexcercise.presentation.movies_screen.MoviesListScreenVM
import com.ofek.moviesexcercise.presentation.movies_screen.MoviesListState
import com.ofek.moviesexcercise.ui.common.EndlessScrollingRv
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider
/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment(), Observer<MoviesListState>,FailedToLoadFirstPageError.HandlingProtocol,FailedToLoadMoreError.HandlingProtocol {

    private lateinit var viewModel: MoviesListScreenVM
    private lateinit var moviesRv: EndlessScrollingRv
    private lateinit var adapter: MoviesListAdapter
    private lateinit var loadingLay: ViewGroup
    private lateinit var pageLoadingLay: ViewGroup
    private lateinit var pageLoadingFailedTv: TextView
    private lateinit var pageLoadingTextTv: TextView
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
        viewModel.errorLiveData.observe(this,
            Observer<PresentationError> {
                it.handle(this)
            })
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
        pageLoadingLay = view.findViewById(R.id.page_loading_lay)
        pageLoadingFailedTv = view.findViewById(R.id.page_loading_failed_tv)
        pageLoadingTextTv = view.findViewById(R.id.page_loading_text_tv)
        moviesRv.onScrollEndedListener = object : EndlessScrollingRv.OnScrollEndedListener {
            override fun onScrollEnded() {
                viewModel.loadNextPage()
            }
        }
    }

    override fun onChanged(t: MoviesListState?) {
        // state is maintained as non-null in the VM so it's safe to extract it explicitly
        if (t!!.loading) {
            if (t.currentPage >= 1 ) {
                pageLoadingTextTv.visibility = View.VISIBLE
                pageLoadingLay.visibility = View.VISIBLE
            } else {
                // the first page should show the loading in the center of the screen
                loadingLay.visibility = View.VISIBLE
            }
        } else {
            pageLoadingLay.postDelayed({
                pageLoadingLay.visibility = View.GONE
                pageLoadingFailedTv.visibility = View.INVISIBLE
            },700)
            loadingLay.visibility = View.GONE
            adapter.moviesList.clear()
            adapter.moviesList.addAll(t.moviesList)
            adapter.notifyDataSetChanged()
        }
    }

    interface InteractionListener {
        fun openFavoritesScreen()
    }

    // this callback will execute when the first page error is handled
    override fun onFailedToLoadFirstPage() {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage("Popular movies failed to load, please check your internet connection and try again.")
            .setNegativeButton("Retry") { dialog, _ ->
                viewModel.loadMoviesList(false)
                dialog.dismiss()
            }.setCancelable(false)
            .create().show()
    }
    // this callback will execute when the load more error is handled
    override fun onFailedToLoadMore() {
        pageLoadingFailedTv.visibility = View.VISIBLE
        pageLoadingTextTv.visibility = View.INVISIBLE
    }
}
