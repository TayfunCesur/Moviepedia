package com.tayfuncesur.moviepedia.ui.topRated

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tayfuncesur.moviepedia.base.Resource
import com.tayfuncesur.moviepedia.model.response.TopRatedResponse
import com.tayfuncesur.moviepedia.repository.TopRatedRepository
import javax.inject.Inject

class TopRatedViewModel @Inject constructor(private var topRatedRepository: TopRatedRepository) : ViewModel() {

    fun getTopRated(apiKey: String, language: String, page: Int): LiveData<Resource<TopRatedResponse>> {
        return topRatedRepository.getTopRated(apiKey, language, page)
    }

    fun search(apiKey: String, language: String, query: String,page: Int): LiveData<Resource<TopRatedResponse>> {
        return topRatedRepository.search(apiKey, language, query,page)
    }

    override fun onCleared() {
        topRatedRepository.clear()
        super.onCleared()
    }

}