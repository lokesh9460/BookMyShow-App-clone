package com.example.bookmyshowapp.data.remote

import com.example.bookmyshowapp.MovieResponse
import com.example.bookmyshowapp.MovieService
import com.example.bookmyshowapp.dao.MovieDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepositoryImp(
        private val movieDao: MovieDao,
        private val request:MovieService
):MovieRepository {

    override fun fetchMovies(api_key: String,onSuccess:(MovieResponse)->Unit,onError:(String)->Unit) {
        val call: Call<MovieResponse> =request.getMovies(api_key)

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                if (response.isSuccessful && response.body()!=null)
                {
                    //success
                    Thread{
                        movieDao.insertMovies(response.body()!!)
                        onSuccess(response.body()!!)
                    }.start()
                    onSuccess(response.body()!!)
                }
                else
                {
                    //error
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //error
                onError("Opps! something went wrong!!")
            }

        })
    }

    override fun getMovieslocal(onSuccess: (MovieResponse?) -> Unit) {
        Thread{
            onSuccess(movieDao.getMovies())
        }
    }

}