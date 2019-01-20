package com.dasilvacarlos.moviesstand.presentation.scenes.favorites

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.domain.app.favorites.*
import com.dasilvacarlos.moviesstand.presentation.generic.GenericFragment
import com.dasilvacarlos.moviesstand.presentation.scenes.detail.DetailsContainerActivity
import com.dasilvacarlos.moviesstand.presentation.scenes.favorites.adapter.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment: GenericFragment(), FavoritesViewLogic {

    private val interactor: FavoritesInteractorLogic = FavoriteInteractor(this)
    private val dataStore: FavoritesDataStore? by lazy { interactor as? FavoritesDataStore }
    private val listAdapter: FavoritesAdapter by lazy { FavoritesAdapter(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareFavoritesList()
        prepareListeners()
    }

    override fun onResume() {
        super.onResume()
        requestFavorites()
    }

    private fun requestFavorites(){
        interactor.requestFavorites(FavoritesUserCases.FavoritesList.Request())
    }

    private fun prepareFavoritesList() {
        val viewManager = GridLayoutManager(context!!, 2)

        favorites_recycler_view.apply {
            layoutManager = viewManager
            adapter = listAdapter
        }
    }

    private fun prepareListeners() {
        listAdapter.itemClick = { index ->
            dataStore?.let { dataStore ->
                val intent = DetailsContainerActivity.getNewIntentDetailed(context!!, dataStore.moviesFavorites, index)
                startActivity(intent)
            }
        }
    }

    override fun displayFavorites(viewModel: FavoritesUserCases.FavoritesList.ViewModel) {
        if(viewModel.items.count() > 0) {
            favorites_empty_text.visibility = View.GONE
            listAdapter.passViewModel(viewModel)
        } else {
            favorites_empty_text.visibility = View.VISIBLE
            listAdapter.clear()
        }
    }
}