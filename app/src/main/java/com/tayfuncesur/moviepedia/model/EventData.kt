package com.tayfuncesur.moviepedia.model

import com.google.gson.annotations.SerializedName

class EventData(@SerializedName("dataTypes") var dataTypes: DataTypes, @SerializedName("data") var data: Any?)