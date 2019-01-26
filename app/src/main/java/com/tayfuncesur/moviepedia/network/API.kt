package com.tayfuncesur.moviepedia.network

import com.tayfuncesur.moviepedia.model.MovieDetail
import com.tayfuncesur.moviepedia.model.response.SearchResponse
import com.tayfuncesur.moviepedia.model.response.TopRatedResponse
import com.tayfuncesur.moviepedia.model.response.UpcomingAndNPResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("movie/top_rated")
    fun getTopRated(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Observable<TopRatedResponse>

    @GET("movie/upcoming")
    fun getUpcoming(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Observable<UpcomingAndNPResponse>

    @GET("movie/now_playing")
    fun getNowplaying(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Observable<UpcomingAndNPResponse>

    @GET("search/movie")
    fun search(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("query") query: String, @Query("page") page: Int): Observable<SearchResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId : Int, @Query("api_key") apiKey: String, @Query("language") language: String): Observable<MovieDetail>

}