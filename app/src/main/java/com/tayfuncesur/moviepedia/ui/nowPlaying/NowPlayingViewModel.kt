package com.tayfuncesur.moviepedia.ui.nowPlaying

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tayfuncesur.moviepedia.base.Resource
import com.tayfuncesur.moviepedia.model.response.UpcomingAndNPResponse
import com.tayfuncesur.moviepedia.repository.NowPlayingRepository
import com.tayfuncesur.moviepedia.repository.TopRatedRepository
import javax.inject.Inject

class NowPlayingViewModel @Inject constructor(private var nowPlayingRepository: NowPlayingRepository) : ViewModel() {

    fun getNowPlaying(apiKey: String, language: String, page: Int) : LiveData<Resource<UpcomingAndNPResponse>> {
        return nowPlayingRepository.getNowPlaying(apiKey, language, page)
    }


    fun search(apiKey: String, language: String, query: String,page: Int): LiveData<Resource<UpcomingAndNPResponse>> {
        return nowPlayingRepository.search(apiKey, language, query,page)
    }

    override fun onCleared() {
        nowPlayingRepository.clear()
        super.onCleared()
    }
}