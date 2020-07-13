package com.ofek.moviesexcercise.ui.movies_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.objects.UiMovie
import java.text.SimpleDateFormat
import java.util.*

class MoviesListAdapter(
    val moviesList: ArrayList<UiMovie>,
    private val listener: OnItemSelectionListener
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_movie_item_lay,parent,false),listener,moviesList)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.titleTv.text = movie.title
        Glide.with(holder.posterIv)
            .load(POSTER_BASE_URL+movie.image)
            // makes glide hold the cached image only for the rest of the day.
            .signature(ObjectKey(SimpleDateFormat("yyyy-mm-dd").format(Calendar.getInstance().time)))
            .into(holder.posterIv)
    }


}
interface OnItemSelectionListener {
    fun onMovieSelected(uiMovie: UiMovie)
}
class ViewHolder(
    itemView: View,
    private val listener: OnItemSelectionListener,
    moviesList: List<UiMovie>
) : RecyclerView.ViewHolder(itemView){
    val titleTv : TextView = itemView.findViewById(R.id.title_tv)
    val posterIv : ImageView = itemView.findViewById(R.id.poster_iv)
    init {
        itemView.setOnClickListener {
            listener.onMovieSelected(moviesList[adapterPosition])
        }
    }
}
