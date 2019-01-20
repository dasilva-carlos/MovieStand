package com.dasilvacarlos.moviesstand.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ResumedMovieModel: Serializable {
    @SerializedName("imdbID")
    val id: String? = null

    @SerializedName("Title")
    val title: String? = null

    @SerializedName("Year")
    val year: String? = null

    @SerializedName("Type")
    val type: String? = null

    @SerializedName("Poster")
    val poster: String? = null
}