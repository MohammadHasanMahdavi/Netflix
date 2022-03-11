package com.example.movies.data.repository

import com.example.movies.data.DataSource
import com.example.movies.data.MyCallback
import com.example.movies.data.models.Movie

class MovieRepository(val dataSource: DataSource = DataSource()) {

    fun getMovies() : ArrayList<Movie>{
       return dataSource.createDataSet()
    }
}