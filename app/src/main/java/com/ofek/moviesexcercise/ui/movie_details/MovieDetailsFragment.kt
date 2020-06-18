package com.ofek.moviesexcercise.ui.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {

    lateinit var titleTv : TextView
    lateinit var yearTv : TextView
    lateinit var ratingTv : TextView
    lateinit var genresTv : TextView
    lateinit var imageIv : ImageView

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTv = view.findViewById(R.id.title_tv_details)
        yearTv = view.findViewById(R.id.year_tv_details)
        ratingTv = view.findViewById(R.id.rating_tv_details)
        genresTv = view.findViewById(R.id.genres_tv_details)
        imageIv = view.findViewById(R.id.image_iv_details)
        arguments.let {
            val movie: UiMovie? = it!!.getParcelable(MOVIE_TAG)
            movie.let {
                titleTv.text = movie!!.title
                yearTv.text = movie.releaseYear.toString()
                ratingTv.text = movie.rating.toString()
                genresTv.text = movie.genre!!.joinToString(",")
                Picasso.get().load(movie.image).into(imageIv)
            }
        }
    }

}
