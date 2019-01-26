package com.tayfuncesur.moviepedia.ui.movieDetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tayfuncesur.moviepedia.base.Resource
import com.tayfuncesur.moviepedia.model.MovieDetail
import com.tayfuncesur.moviepedia.repository.MovieDetailRepository
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private var movieDetailRepository: MovieDetailRepository) : ViewModel() {

    fun getMovieDetail(movieId: Int, apiKey: String, language: String): LiveData<Resource<MovieDetail>> {
        return movieDetailRepository.getMovieDetail(movieId, apiKey, language)
    }

    override fun onCleared() {
        movieDetailRepository.clear()
        super.onCleared()
    }
}