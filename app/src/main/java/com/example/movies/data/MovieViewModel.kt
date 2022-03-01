package com.example.movies.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.models.Movie

class MovieViewModel : ViewModel() {
    var movieList: MutableLiveData<List<Movie>> = MutableLiveData(DataSource.createDataSet())
}