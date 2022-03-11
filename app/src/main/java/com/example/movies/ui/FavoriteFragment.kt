package com.example.movies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var model : Lazy<MovieViewModel> = activityViewModels()
    private lateinit var movieAdapter : MovieRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        addDataSet()

    }


    fun addDataSet(){

        val data = model.value.movieList.value?.filter {
            it.isFavorite
        }
        if (data != null) {
            movieAdapter.submitList(data)
        }
    }
    private fun initRecycler(){
        var recyclerView: RecyclerView? = view?.findViewById(R.id.movieRecycler2)

        recyclerView?.layoutManager = GridLayoutManager(context,3)
        val topSpacingItemDecoration = TopSpacingItemDecoration(30)
        recyclerView?.addItemDecoration(topSpacingItemDecoration)
        movieAdapter = MovieRecyclerAdapter()
        recyclerView?.adapter = movieAdapter
    }
}