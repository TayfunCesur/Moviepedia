package com.tayfuncesur.moviepedia.model

import com.google.gson.annotations.SerializedName

class Dates(
    @SerializedName("maximum") var maximum: String,
    @SerializedName("minimum") var minimum: String
)