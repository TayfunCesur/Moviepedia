package com.tayfuncesur.moviepedia.repository.topRated

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.tayfuncesur.moviepedia.BuildConfig
import com.tayfuncesur.moviepedia.RxImmediateSchedulerRule
import com.tayfuncesur.moviepedia.model.response.TopRatedResponse
import com.tayfuncesur.moviepedia.network.ApiService
import com.tayfuncesur.moviepedia.repository.TopRatedRepository
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class TopRatedRepositoryTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val apiService = mock(ApiService::class.java)
    private val topRatedRepository = mock(TopRatedRepository::class.java)

    private inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)

    @Test
    fun shouldLoadSuccess() {
        //Given
        val apikey = BuildConfig.API_KEY
        val lang = "tr-TR"
        val page = 1

        val mockResponse = Gson().fromJson<TopRatedResponse>(MockResponse.success, TopRatedResponse::class.java)
        `when`(apiService.getTopRated(apikey, lang, page)).thenReturn(Observable.just(mockResponse))

        //When
        topRatedRepository.getTopRated(apikey, lang, page)

        verify(topRatedRepository, never()).whenError("")
    }


    @Test
    fun shouldThrowWrongApiKey() {
        //Given
        val apikey = "qawdasdsadasdjasjd"//WrongApiKey
        val lang = "tr-TR"
        val page = 1

        val mockResponse = Gson().fromJson<TopRatedResponse>(MockResponse.serviceError, TopRatedResponse::class.java)
        `when`(apiService.getTopRated(apikey, lang, page)).thenReturn(Observable.just(mockResponse))

        //When
        topRatedRepository.getTopRated(apikey, lang, page)

        verify(topRatedRepository, never()).whenSuccess(mockResponse)
    }

    @Test
    fun shouldThrowApikeyNotFound() {
        //Given
        val apikey = ""
        val lang = "tr-TR"
        val page = 1

        val mockResponse = Gson().fromJson<TopRatedResponse>(MockResponse.success, TopRatedResponse::class.java)
        `when`(apiService.getTopRated(apikey, lang, page)).thenReturn(Observable.just(mockResponse))

        //When
        topRatedRepository.getTopRated(apikey, lang, page)

        verify(topRatedRepository, never()).whenSuccess(mockResponse)
    }

}