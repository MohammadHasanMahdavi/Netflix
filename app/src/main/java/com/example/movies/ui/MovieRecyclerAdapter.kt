package com.example.movies.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.models.Movie

class MovieRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var list : List<Movie> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MovieViewHolder -> {
                holder.bind(list[position])
            }
        }
    }

    fun submitList(movieList : List<Movie>)
    {
        list = movieList
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MovieViewHolder(
        itemView : View
    ):RecyclerView.ViewHolder(itemView){
        val poster : ImageView = itemView.findViewById(R.id.MovieImage)
        val title : TextView = itemView.findViewById(R.id.MovieTitle)
        val favorite: ImageView = itemView.findViewById(R.id.MovieIsFavorite)

        fun bind(movie:Movie)
        {
            title.text = movie.title
            if (movie.isFavorite)
                favorite.setImageResource(R.drawable.liked_icon)
            else
                favorite.setImageResource(R.drawable.unliked_icon)

            favorite.setOnClickListener {
                if (!movie.isFavorite) {
                    favorite.setImageResource(R.drawable.liked_icon)
                    movie.isFavorite= true
                }else {
                    favorite.setImageResource(R.drawable.unliked_icon)
                    movie.isFavorite = false
                }
            }
            poster.setImageResource(movie.id)
        }
    }

}