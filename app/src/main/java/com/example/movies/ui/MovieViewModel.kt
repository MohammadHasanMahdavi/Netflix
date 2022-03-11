package com.example.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.DataSource
import com.example.movies.data.models.Movie
import com.example.movies.data.repository.MovieRepository

class MovieViewModel(val movieRepository: MovieRepository = MovieRepository()) : ViewModel() {
    val movieList : MutableLiveData<ArrayList<Movie>> by lazy {
        MutableLiveData()
    }

    init {
        movieList.value = movieRepository.getMovies()
    }
}