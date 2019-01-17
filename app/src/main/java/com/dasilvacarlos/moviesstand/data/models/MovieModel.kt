package com.dasilvacarlos.moviesstand.data.models

import com.google.gson.annotations.SerializedName


class Movie {
    @SerializedName("Title")
    val title: String? = null

    @SerializedName("Year")
    val year: String? = null

    @SerializedName("Rated")
    val rated: String? = null

    @SerializedName("Released")
    val released: String? = null

    @SerializedName("Runtime")
    val runtime: String? = null

    @SerializedName("Genre")
    val genre: String? = null

    @SerializedName("Director")
    val director: String? = null

    @SerializedName("Writer")
    val writer: String? = null

    @SerializedName("Actors")
    val actors: String? = null

    @SerializedName("Plot")
    val plot: String? = null

    @SerializedName("")

    "Language":"English","Country":"Canada, UK, France","Awards":"13 wins & 24 nominations.","Poster":"https://m.media-amazon.com/images/M/MV5BMmY4OGRmNWMtNmIyNS00YWQ5LWJmMGUtMDI3MWRlMmQ0ZDQzL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg","Ratings":[{"Source":"Internet Movie Database","Value":"6.8/10"},{"Source":"Rotten Tomatoes","Value":"84%"},{"Source":"Metacritic","Value":"83/100"}],"Metascore":"83","imdbRating":"6.8","imdbVotes":"33,124","imdbID":"tt0278731","Type":"movie","DVD":"29 Jul 2003","BoxOffice":"$1,278,369","Production":"Sony Pictures Classics","Website":"http://www.spiderthemovie.com/","Response":"True"}
}