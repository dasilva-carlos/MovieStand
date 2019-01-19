package com.dasilvacarlos.moviesstand.data.models

import com.google.gson.annotations.SerializedName


class SearchMovieResultModel {
    @SerializedName("Search")
    var search: List<ResumedMovieModel>? = null

    @SerializedName("totalResults")
    var totalResults: Int? = null

    @SerializedName("Response")
    var response: Boolean? = null

    @SerializedName("Error")
    var error: String? = null
}