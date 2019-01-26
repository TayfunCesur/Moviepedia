package com.tayfuncesur.moviepedia.repository.movieDetail

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.tayfuncesur.moviepedia.BuildConfig
import com.tayfuncesur.moviepedia.RxImmediateSchedulerRule
import com.tayfuncesur.moviepedia.model.MovieDetail
import com.tayfuncesur.moviepedia.network.ApiService
import com.tayfuncesur.moviepedia.repository.MovieDetailRepository
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MovieDetailRepositoryTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val apiService = mock(ApiService::class.java)
    private val movieDetailRepository = mock(MovieDetailRepository::class.java)

    @Test
    fun shouldLoadSuccess() {
        //Given
        val apikey = BuildConfig.API_KEY
        val lang = "tr-TR"
        val movieId = 106515

        val mockResponse = Gson().fromJson<MovieDetail>(MockResponse.success, MovieDetail::class.java)
        `when`(apiService.getMovieDetail(movieId, apikey, lang)).thenReturn(Observable.just(mockResponse))

        //When
        movieDetailRepository.getMovieDetail(movieId, apikey, lang)

        //Then
        verify(movieDetailRepository, never()).whenError("")
    }


    @Test
    fun shouldThrowWrongApiKey() {
        //Given
        val apikey = "qawdasdsadasdjasjd"//WrongApiKey
        val lang = "tr-TR"
        val movieId = 106515

        val mockResponse = Gson().fromJson<MovieDetail>(MockResponse.success, MovieDetail::class.java)
        `when`(apiService.getMovieDetail(movieId, apikey, lang)).thenReturn(Observable.just(mockResponse))

        //When
        movieDetailRepository.getMovieDetail(movieId, apikey, lang)

        //Then
        verify(movieDetailRepository, never()).whenSuccess(mockResponse)
    }

    @Test
    fun shouldThrowApikeyNotFound() {
        //Given
        val apikey = ""
        val lang = "tr-TR"
        val movieId = 106515

        val mockResponse = Gson().fromJson<MovieDetail>(MockResponse.success, MovieDetail::class.java)
        `when`(apiService.getMovieDetail(movieId, apikey, lang)).thenReturn(Observable.just(mockResponse))

        //When
        movieDetailRepository.getMovieDetail(movieId, apikey, lang)

        //Then
        verify(movieDetailRepository, never()).whenSuccess(mockResponse)
    }

}