package com.tayfuncesur.moviepedia.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tayfuncesur.moviepedia.base.Repository
import com.tayfuncesur.moviepedia.base.Resource
import com.tayfuncesur.moviepedia.model.MovieDetail
import com.tayfuncesur.moviepedia.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepository @Inject constructor(var apiService: ApiService) : Repository() {

    private val result = MutableLiveData<Resource<MovieDetail>>()

    fun getMovieDetail(movieId: Int, apiKey: String, language: String): LiveData<Resource<MovieDetail>> {
        if (apiKey.isEmpty()) {
            whenError("Apikey is required but not found!")
        } else {
            addDisposable(apiService.getMovieDetail(movieId, apiKey, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { whenStart() }
                .subscribe(
                    { result -> whenSuccess(result) },
                    { cause -> whenError(cause.toString()) }
                ))
        }
        return result
    }

    public fun whenStart() {
        result.value = Resource.whenLoading(null)
    }

    public fun whenSuccess(movieDetail: MovieDetail) {
        result.value = Resource.whenSuccess(movieDetail)
    }

    public fun whenError(cause: String) {
        result.value = Resource.whenError(cause)
    }

}