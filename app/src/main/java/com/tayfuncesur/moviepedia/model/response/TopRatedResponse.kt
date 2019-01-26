package com.tayfuncesur.moviepedia.model.response

import com.google.gson.annotations.SerializedName
import com.tayfuncesur.moviepedia.model.Movie

class TopRatedResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("results") var movies: List<Movie>
)