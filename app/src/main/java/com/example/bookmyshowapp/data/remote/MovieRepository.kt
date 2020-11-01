package com.example.bookmyshowapp.data.remote

import com.example.bookmyshowapp.MovieResponse

interface MovieRepository {
    fun fetchMovies(api_key:String, onSuccess:(MovieResponse)-> Unit, onError:(String)->Unit)

    fun getMovieslocal(onSuccess: (MovieResponse?) -> Unit)
}
