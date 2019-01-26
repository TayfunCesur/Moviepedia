package com.tayfuncesur.moviepedia.ui.upcoming

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tayfuncesur.moviepedia.base.Resource
import com.tayfuncesur.moviepedia.model.response.TopRatedResponse
import com.tayfuncesur.moviepedia.model.response.UpcomingAndNPResponse
import com.tayfuncesur.moviepedia.repository.TopRatedRepository
import com.tayfuncesur.moviepedia.repository.UpcomingRepository
import javax.inject.Inject

class UpcomingViewModel @Inject constructor(private var upcomingRepository: UpcomingRepository) : ViewModel() {

    fun getUpcoming(apiKey: String, language: String, page: Int): LiveData<Resource<UpcomingAndNPResponse>> {
        return upcomingRepository.getUpcoming(apiKey, language, page)
    }

    fun search(apiKey: String, language: String, query: String,page: Int): LiveData<Resource<UpcomingAndNPResponse>> {
        return upcomingRepository.search(apiKey, language, query,page)
    }

    override fun onCleared() {
        upcomingRepository.clear()
        super.onCleared()
    }

}