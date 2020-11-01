package com.example.bookmyshowapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookmyshowapp.MainViewModel
import com.example.bookmyshowapp.NetworkHelper
import com.example.bookmyshowapp.data.remote.MovieRepository


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory (private val networkHelper: NetworkHelper, private val movieRepository: MovieRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(networkHelper, movieRepository) as T
    }
}
