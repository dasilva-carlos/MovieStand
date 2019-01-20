package com.dasilvacarlos.moviesstand.domain.app.list

import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.data.library.DateHelper
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesProvider


class ListPresenter(val view: ListViewLogic): ListPresenterLogic {
    override fun presentFavorites(response: ListUserCases.FavoritesList.Result) {
       val items: ArrayList<ListUserCases.FavoritesList.ViewModel.Item> = arrayListOf()
       var lastHeader: String? = null
       response.favorites.forEach {
           val header = resolveHeaderName(it, response.orderBy)
           if(header != lastHeader) {
               items.add(ListUserCases.FavoritesList.ViewModel.Header(header))
           }
           items.add(ListUserCases.FavoritesList.ViewModel.Movie(it.title ?: "-"))
           lastHeader = header
       }
        view.displayFavorites(ListUserCases.FavoritesList.ViewModel(items))
    }

    override fun presentError(request: Any, serviceError: ServiceError) {
        view.displayError(request, error = serviceError)
    }

    private fun resolveHeaderName(movieModel: MovieModel, orderBy: FavoritesProvider.OrderByEnum): String {
        return when(orderBy) {
            FavoritesProvider.OrderByEnum.TITLE -> {
                movieModel.title?.let {
                    return if(it.count() == 0) {
                        "-"
                    } else {
                        val character = it[0]
                        if(character.isLetter()) character.toString().toUpperCase() else "#"
                    }
                }
                return "-"
            }
            FavoritesProvider.OrderByEnum.RATING -> {
               try{
                   movieModel.imdbRating?.substringBefore(".")?.toInt().toString()
               } catch (e: NumberFormatException) {
                   return "-"
               }
            }
            FavoritesProvider.OrderByEnum.RELEASE -> {
                movieModel.released?.let{
                    return DateHelper.convertDateFormat(it,
                            DateHelper.DateFormatType.OMDB_FORMAT,
                            DateHelper.DateFormatType.YEAR_FORMAT) ?: "-"
                }
                return "-"
            }
            FavoritesProvider.OrderByEnum.RANDOM -> "-"
        }
    }
}