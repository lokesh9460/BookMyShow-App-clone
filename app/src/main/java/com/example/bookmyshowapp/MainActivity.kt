package com.example.bookmyshowapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.bookmyshowapp.data.remote.MovieDatabase
import com.example.bookmyshowapp.data.remote.MovieRepositoryImp
import com.example.bookmyshowapp.util.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class  MainActivity : AppCompatActivity() {

    private lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        observeViewModel()
    }
    private fun setupViewModel()
    {
        showProgress()
        viewModel=ViewModelProvider(
                this,MainViewModelFactory(
                NetworkHelper(this),
                MovieRepositoryImp(MovieDatabase.getInstance(this).movieDao(),RetrofitBuilder.buildService())

        )
        )[MainViewModel::class.java]
        viewModel.onCreate()

    }
    private fun observeViewModel()
    {
        viewModel.movieResponse.observe(this, Observer { it: MovieResponse ->
            showMovies(it.results as ArrayList<Movie>)
            hideProgress()
        })
        viewModel.errorResponse.observe(this, Observer { it: String ->
            showErrorMessage(it)
            hideProgress()
        })
    }

    private fun showMovies(movies: ArrayList<Movie>)
    {
        recyclerView.visibility=View.VISIBLE
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator=DefaultItemAnimator()
        recyclerView.adapter=MovieAdapter(movies)
    }
    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }
    private fun hideProgress(){
        progressBar.visibility=View.GONE
    }
    private fun showErrorMessage(errorMessage: String?)
    {
        errorView.visibility=View.VISIBLE
        errorView.text=errorMessage
    }

}