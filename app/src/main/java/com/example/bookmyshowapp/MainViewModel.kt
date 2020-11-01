package com.example.bookmyshowapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookmyshowapp.data.remote.MovieRepository

class MainViewModel (
        private val networkHelper: NetworkHelper,
        private val movieRepository: MovieRepository): ViewModel()
{
    companion object {
        private const val API_KEY = "0a949a9678a76973a4c17d33dbcd0af5"
        private const val SOMETHING_WENT_WRONG = "Something went wrong!"
    }


    private val _movieResponse= MutableLiveData<MovieResponse>()
    val movieResponse:LiveData<MovieResponse>
        get()=_movieResponse
    private val _errorResponse=MutableLiveData<String>()
    val errorResponse:LiveData<String>
        get()=_errorResponse

    fun onCreate()
    {
        if(networkHelper.isNetworkConnected()){
            movieRepository.fetchMovies(API_KEY,{movieResponse->_movieResponse.postValue(movieResponse)
            }, {
                _errorResponse.postValue(it)
            })
        }else{
            movieRepository.getMovieslocal { movieResponse ->
                if(movieResponse!=null && movieResponse.results.isNotEmpty()){
                    _movieResponse.postValue(movieResponse)
                }
                else
                {
                    _errorResponse.postValue(SOMETHING_WENT_WRONG)
                }
            }
        }
    }
}