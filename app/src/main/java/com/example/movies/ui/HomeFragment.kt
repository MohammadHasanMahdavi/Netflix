package com.example.movies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.models.Movie


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var model : Lazy<MovieViewModel> = activityViewModels()
    private lateinit var movieAdapter : MovieRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.value.movieList.observe(viewLifecycleOwner){
            if (it!=null) {
                initRecycler()
                addDataSet(it)
            }
        }

    }
    private fun addDataSet(movieList : ArrayList<Movie>){
        movieAdapter.submitList(movieList)
    }
    private fun initRecycler(){
        var recyclerView: RecyclerView? = view?.findViewById(R.id.movieRecycler)


        recyclerView?.layoutManager = GridLayoutManager(context,3)
        val topSpacingItemDecoration = TopSpacingItemDecoration(30)
        recyclerView?.addItemDecoration(topSpacingItemDecoration)
        movieAdapter = MovieRecyclerAdapter()
        recyclerView?.adapter = movieAdapter
    }
}
