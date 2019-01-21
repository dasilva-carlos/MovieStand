package com.dasilvacarlos.moviesstand.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class RatingModel: Serializable {
    @SerializedName("Source")
    val source: String? = null

    @SerializedName("Value")
    val value: String? = null
}