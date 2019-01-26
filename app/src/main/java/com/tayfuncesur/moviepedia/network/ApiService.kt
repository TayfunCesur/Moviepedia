package com.tayfuncesur.moviepedia.network

import com.tayfuncesur.moviepedia.model.MovieDetail
import com.tayfuncesur.moviepedia.model.response.SearchResponse
import com.tayfuncesur.moviepedia.model.response.TopRatedResponse
import com.tayfuncesur.moviepedia.model.response.UpcomingAndNPResponse
import io.reactivex.Observable
import javax.inject.Inject

class ApiService @Inject constructor(var api: API) {

    fun getTopRated(apiKey: String, language: String, page: Int): Observable<TopRatedResponse> {
        return api.getTopRated(apiKey, language, page)
    }

    fun getUpcoming(apiKey: String, language: String, page: Int): Observable<UpcomingAndNPResponse> {
        return api.getUpcoming(apiKey, language, page)
    }

    fun getNowplaying(apiKey: String, language: String, page: Int): Observable<UpcomingAndNPResponse> {
        return api.getNowplaying(apiKey, language, page)
    }

    fun search(apiKey: String, language: String, query: String, page: Int): Observable<SearchResponse> {
        return api.search(apiKey, language, query, page)
    }

    fun getMovieDetail(movieId: Int, apiKey: String, language: String): Observable<MovieDetail> {
        return api.getMovieDetail(movieId, apiKey, language)
    }
}