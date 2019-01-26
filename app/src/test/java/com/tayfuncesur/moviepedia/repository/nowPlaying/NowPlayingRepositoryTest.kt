package com.tayfuncesur.moviepedia.repository.nowPlaying

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.tayfuncesur.moviepedia.BuildConfig
import com.tayfuncesur.moviepedia.RxImmediateSchedulerRule
import com.tayfuncesur.moviepedia.model.response.UpcomingAndNPResponse
import com.tayfuncesur.moviepedia.network.ApiService
import com.tayfuncesur.moviepedia.repository.NowPlayingRepository
import com.tayfuncesur.moviepedia.repository.upcoming.MockResponse
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class NowPlayingRepositoryTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val apiService = mock(ApiService::class.java)
    private val nowPlayingRepository = mock(NowPlayingRepository::class.java)

    @Test
    fun shouldLoadSuccess() {
        //Given
        val apikey = BuildConfig.API_KEY
        val lang = "tr-TR"
        val page = 1

        val mockResponse = Gson().fromJson<UpcomingAndNPResponse>(MockResponse.success, UpcomingAndNPResponse::class.java)
        `when`(apiService.getNowplaying(apikey, lang, page)).thenReturn(Observable.just(mockResponse))

        //When
        nowPlayingRepository.getNowPlaying(apikey, lang, page)

        //Then
        verify(nowPlayingRepository, never()).whenError("")
    }


    @Test
    fun shouldThrowWrongApiKey() {
        //Given
        val apikey = "qawdasdsadasdjasjd"//WrongApiKey
        val lang = "tr-TR"
        val page = 1

        val mockResponse = Gson().fromJson<UpcomingAndNPResponse>(MockResponse.serviceError, UpcomingAndNPResponse::class.java)
        `when`(apiService.getNowplaying(apikey, lang, page)).thenReturn(Observable.just(mockResponse))

        //When
        nowPlayingRepository.getNowPlaying(apikey, lang, page)

        verify(nowPlayingRepository, never()).whenSuccess(mockResponse)
    }

    @Test
    fun shouldThrowApikeyNotFound() {
        //Given
        val apikey = ""
        val lang = "tr-TR"
        val page = 1

        val mockResponse = Gson().fromJson<UpcomingAndNPResponse>(MockResponse.success, UpcomingAndNPResponse::class.java)
        `when`(apiService.getNowplaying(apikey, lang, page)).thenReturn(Observable.just(mockResponse))

        //When
        nowPlayingRepository.getNowPlaying(apikey, lang, page)

        verify(nowPlayingRepository, never()).whenSuccess(mockResponse)
    }

}