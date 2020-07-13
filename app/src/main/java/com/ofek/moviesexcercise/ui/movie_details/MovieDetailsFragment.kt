package com.ofek.moviesexcercise.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider
import com.ofek.moviesexcercise.ui.movies_list.MoviesListAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple fragment to show the details of the movie
 */
class MovieDetailsFragment : Fragment() {

    lateinit var titleTv : TextView
    lateinit var yearTv : TextView
    lateinit var ratingTv : TextView
    lateinit var genresTv : TextView
    lateinit var posterIv : ImageView
    lateinit var favoriteIv : ImageView

    private val presenter = GlobalDependencyProvider.provideDetailsScreenPresenter()


    companion object {
        private const val MOVIE_TAG: String = "movie"

        fun newInstance(uiMovie: UiMovie) : MovieDetailsFragment {
            val movieDetailsFrag = MovieDetailsFragment()
            val args = Bundle()
            args.putParcelable(MOVIE_TAG,uiMovie)
            movieDetailsFrag.arguments = args
            return movieDetailsFrag
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearResources()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTv = view.findViewById(R.id.title_tv_details)
        yearTv = view.findViewById(R.id.year_tv_details)
        ratingTv = view.findViewById(R.id.rating_tv_details)
        genresTv = view.findViewById(R.id.overview_tv)
        posterIv = view.findViewById(R.id.image_iv_details)
        favoriteIv = view.findViewById(R.id.favorite_iv)
        arguments?.let { bundle ->
            val movie: UiMovie? = bundle.getParcelable(MOVIE_TAG)
            movie?.let { uiMovie ->
                titleTv.text = uiMovie.title
                yearTv.text = uiMovie.releaseYear.toString()
                ratingTv.text = uiMovie.rating.toString()
                Glide.with(view.context)
                    .load(MoviesListAdapter.POSTER_BASE_URL +uiMovie.image)
                    // makes glide hold the cached image only for the rest of the day.
                    .signature(ObjectKey(SimpleDateFormat("yyyy-mm-dd").format(Calendar.getInstance().time)))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .fitCenter()
                    .into(posterIv)
                if (uiMovie.favorite) {
                    favoriteIv.isSelected = true
                    favoriteIv.setImageResource(R.drawable.ic_favorite_orange_24dp)
                } else {
                    favoriteIv.setImageResource(R.drawable.ic_favorite_gray_24dp)
                    favoriteIv.isSelected = false
                }
                favoriteIv.setOnClickListener {
                    if (it.isSelected) {
                        favoriteIv.setImageResource(R.drawable.ic_favorite_gray_24dp)
                        it.isSelected = false
                        presenter.removeMovieFromFavorites(uiMovie)
                    } else {
                        favoriteIv.setImageResource(R.drawable.ic_favorite_orange_24dp)
                        it.isSelected = true
                        presenter.addMovieToFavorites(uiMovie)
                    }
                }
            }
        }
    }

}
