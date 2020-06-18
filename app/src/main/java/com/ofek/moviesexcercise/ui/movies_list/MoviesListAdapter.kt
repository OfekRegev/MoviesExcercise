package com.ofek.moviesexcercise.ui.movies_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.objects.UiMovie

class MoviesListAdapter(
    private val moviesList: List<UiMovie>,
    val listener: OnItemSelectionListener
) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_movie_item_lay,parent,false),listener,moviesList)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.titleTv.text = movie.title
        holder.yearTv.text = movie.releaseYear.toString()
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
    val yearTv : TextView = itemView.findViewById(R.id.year_tv)
    init {
        itemView.setOnClickListener {
            listener.onMovieSelected(moviesList[adapterPosition])
        }
    }
}
