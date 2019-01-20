package com.dasilvacarlos.moviesstand.data.workers.favorites

import android.os.Handler
import android.os.Looper
import com.activeandroid.query.Select
import com.dasilvacarlos.moviesstand.data.models.MovieDatabaseModel
import com.dasilvacarlos.moviesstand.data.models.MovieModel


class FavoritesWorker(private val receiver: FavoritesReceiver): FavoritesProvider {

    override fun saveAsFavorite(movie: MovieModel) {
        val id = MovieDatabaseModel(movie).save()
        if(id == -1L) {
            receiver.onSaveResult(false, movie.id)
        } else {
            receiver.onSaveResult(true, movie.id)
        }
    }

    override fun removeFavorite(movieId: String) {
        Thread {
            var hasDeleted = false
            val movies = Select()
                    .from(MovieDatabaseModel::class.java)
                    .where("${MovieDatabaseModel.ID_KEY} = '$movieId'")
                    .execute<MovieDatabaseModel>()

            movies.forEach{
                it.delete()
                hasDeleted = true
            }

            val handler = Handler(Looper.getMainLooper())
            handler.post {
                receiver.onRemoveResult(hasDeleted, movieId)
            }
        }.run()
    }

    override fun checkIfIsFavorite(ids: List<String>) {
        Thread {
            var concatenatedIds = ""
            val map = HashMap<String, Boolean>()
            ids.forEach {
                concatenatedIds = "'$concatenatedIds$it',"
                map[it] = false
            }
            concatenatedIds = concatenatedIds.removeSuffix(",")

            val movies = Select()
                            .from(MovieDatabaseModel::class.java)
                            .where("${MovieDatabaseModel.ID_KEY} IN ($concatenatedIds)")
                            .execute<MovieDatabaseModel>()

            movies.forEach {
                map[it.id] = true
            }

            val handler = Handler(Looper.getMainLooper())
            handler.post {
                receiver.onFavoritesCheckResult(isFavorite = map)
            }
        }.run()
    }

    override fun getFavoritesList(orderBy: FavoritesProvider.OrderByEnum) {
        Thread {
            val orderByClause = when(orderBy) {
                FavoritesProvider.OrderByEnum.TITLE -> MovieDatabaseModel.ALL_CAPS_TITLE_KEY
                FavoritesProvider.OrderByEnum.RATING -> MovieDatabaseModel.RATING_KEY
                FavoritesProvider.OrderByEnum.RELEASE -> MovieDatabaseModel.RELEASED_KEY
                FavoritesProvider.OrderByEnum.RANDOM -> "RANDOM()"
            }

            val movies = Select()
                    .from(MovieDatabaseModel::class.java)
                    .orderBy(orderByClause)
                    .execute<MovieDatabaseModel>()

            val response = movies.map { it.getMovieModel() }

            val handler = Handler(Looper.getMainLooper())
            handler.post {
                receiver.onFavoritesList(response, orderBy)
            }
        }.run()
    }
}