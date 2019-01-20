package com.dasilvacarlos.moviesstand.data.models

import com.activeandroid.DatabaseHelper
import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.dasilvacarlos.moviesstand.data.library.DateHelper
import java.lang.Exception

@Table(name = "FavoriteMovies")
class MovieDatabaseModel(): Model() {

    companion object {
        const val ID_KEY = "imdbID"
        const val TITLE_KEY = "Title"
        const val RELEASED_KEY = "Released"
        const val RATING_KEY = "imdbRating"
        const val ALL_CAPS_TITLE_KEY = "AllCapsTitle"
    }

    constructor(movie: MovieModel) : this() {
        this.id = movie.id ?: ""
        this.title = movie.title
        this.titleAllCaps = movie.title?.toUpperCase()
        this.year = movie.year
        this.rated = movie.rated
        this.released = movie.released?.let {
            DateHelper.convertDateFormat(it,
                    DateHelper.DateFormatType.OMDB_FORMAT,
                    DateHelper.DateFormatType.DATABASE_FORMAT)
        }
        this.runtime = movie.runtime
        this.genre = movie.genre
        this.director = movie.director
        this.writer = movie.writer
        this.actors = movie.actors
        this.plot = movie.plot
        this.language = movie.language
        this.country = movie.country
        this.awards = movie.awards
        this.poster = movie.poster
        this.metascore = movie.metascore
        this.imdbRating = try{ movie.imdbRating?.toFloat()} catch (e: NumberFormatException) { null }
        this.imdbVotes = movie.imdbVotes
        this.type = movie.type
        this.dvd = movie.dvd
        this.boxOffice = movie.boxOffice
        this.production = movie.production
        this.website = movie.website
    }

    @Column(name = ID_KEY, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    var id: String = ""

    @Column(name = TITLE_KEY)
    var title: String? = null

    @Column(name = ALL_CAPS_TITLE_KEY)
    var titleAllCaps: String? = null

    @Column(name = "Year")
    var year: String? = null

    @Column(name = "Rated")
    var rated: String? = null

    @Column(name = RELEASED_KEY)
    var released: String? = null

    @Column(name = "Runtime")
    var runtime: String? = null

    @Column(name = "Genre")
    var genre: String? = null

    @Column(name = "Director")
    var director: String? = null

    @Column(name = "Writer")
    var writer: String? = null

    @Column(name = "Actors")
    var actors: String? = null

    @Column(name = "Plot")
    var plot: String? = null

    @Column(name = "Language")
    var language: String? = null

    @Column(name = "Country")
    var country: String? = null

    @Column(name = "Awards")
    var awards: String? = null

    @Column(name = "Poster")
    var poster: String? = null

    @Column(name = "Metascore")
    var metascore: String? = null

    @Column(name = RATING_KEY)
    var imdbRating: Float? = null

    @Column(name = "imdbVotes")
    var imdbVotes: String? = null

    @Column(name = "Type")
    var type: String? = null

    @Column(name = "DVD")
    var dvd: String? = null

    @Column(name = "BoxOffice")
    var boxOffice: String? = null

    @Column(name = "Production")
    var production: String? = null

    @Column(name = "Website")
    var website: String? = null

    fun getMovieModel(): MovieModel {
        val movieModel = MovieModel()
        movieModel.id = this.id
        movieModel.title = this.title
        movieModel.year = this.year
        movieModel.rated = this.rated
        movieModel.released = this.released?.let {
            DateHelper.convertDateFormat(it,
                    DateHelper.DateFormatType.DATABASE_FORMAT,
                    DateHelper.DateFormatType.OMDB_FORMAT
            )
        }
        movieModel.runtime = this.runtime
        movieModel.genre = this.genre
        movieModel.director = this.director
        movieModel.writer = this.writer
        movieModel.actors = this.actors
        movieModel.plot = this.plot
        movieModel.language = this.language
        movieModel.country = this.country
        movieModel.awards = this.awards
        movieModel.poster = this.poster
        movieModel.metascore = this.metascore
        movieModel.imdbRating = this.imdbRating.toString()
        movieModel.imdbVotes = this.imdbVotes
        movieModel.type = this.type
        movieModel.dvd = this.dvd
        movieModel.boxOffice = this.boxOffice
        movieModel.production = this.production
        movieModel.website = this.website
        movieModel.response = true
        movieModel.error = null
        return movieModel
    }
}