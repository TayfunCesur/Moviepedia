package com.tayfuncesur.moviepedia.model.response

import com.google.gson.annotations.SerializedName
import com.tayfuncesur.moviepedia.model.Dates
import com.tayfuncesur.moviepedia.model.Movie

class UpcomingAndNPResponse(
    @SerializedName("results") var movies: List<Movie>,
    @SerializedName("page") var page: Int,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("dates") var dates: Dates,
    @SerializedName("total_pages") var totalPages: Int
)