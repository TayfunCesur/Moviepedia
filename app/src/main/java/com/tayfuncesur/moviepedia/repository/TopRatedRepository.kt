package com.tayfuncesur.moviepedia.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tayfuncesur.moviepedia.base.Repository
import com.tayfuncesur.moviepedia.base.Resource
import com.tayfuncesur.moviepedia.model.response.SearchResponse
import com.tayfuncesur.moviepedia.model.response.TopRatedResponse
import com.tayfuncesur.moviepedia.network.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedRepository @Inject constructor(var apiService: ApiService) : Repository() {

    private val result = MutableLiveData<Resource<TopRatedResponse>>()

    fun getTopRated(apiKey: String, language: String, page: Int): LiveData<Resource<TopRatedResponse>> {
        if (apiKey.isEmpty()) {
            whenError("Apikey is required but not found!")
        } else {
            addDisposable(apiService.getTopRated(apiKey, language, page)
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

    fun search(apiKey: String, language: String, query: String,page: Int): LiveData<Resource<TopRatedResponse>> {
        if (apiKey.isEmpty()) {
            whenError("Apikey is required but not found!")
        } else {
            addDisposable(apiService.search(apiKey, language, query,page)
                .map { it: SearchResponse ->
                    TopRatedResponse(it.page, it.totalResults, it.totalPages, it.movies)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { whenStart() }
                .subscribe(
                    { result -> whenSuccess(result) },
                    { cause -> whenError(cause.toString()) }
                )


            )
        }

        return result
    }

    public fun whenStart() {
        result.value = Resource.whenLoading(null)
    }

    public fun whenSuccess(topRatedResponse: TopRatedResponse) {
        result.value = Resource.whenSuccess(topRatedResponse)
    }

    public fun whenError(cause: String) {
        result.value = Resource.whenError(cause)
    }

}