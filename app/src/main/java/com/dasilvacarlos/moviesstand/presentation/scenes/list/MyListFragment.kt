package com.dasilvacarlos.moviesstand.presentation.scenes.list

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.data.workers.favorites.FavoritesProvider
import com.dasilvacarlos.moviesstand.domain.app.list.*
import com.dasilvacarlos.moviesstand.presentation.generic.GenericFragment
import com.dasilvacarlos.moviesstand.presentation.scenes.detail.DetailsContainerActivity
import com.dasilvacarlos.moviesstand.presentation.scenes.list.adapter.ListAdapter
import kotlinx.android.synthetic.main.fragment_my_list.*


class MyListFragment: GenericFragment(), ListViewLogic, View.OnClickListener {

    private enum class OrderSelect {
        TITLE,
        RATING,
        RELEASE
    }

    private val interactor: ListInteractorLogic = ListInteractor(this)
    private val dataStore: ListDataStore? by lazy { interactor as? ListDataStore }
    private var orderSelect: OrderSelect = OrderSelect.TITLE
    private val listAdapter: ListAdapter by lazy { ListAdapter(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        refreshButtonState()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    override fun onClick(v: View?) {
        val lastButtonState = orderSelect
        when(v?.id) {
            R.id.list_button_title -> orderSelect = OrderSelect.TITLE
            R.id.list_button_rating -> orderSelect = OrderSelect.RATING
            R.id.list_button_release -> orderSelect = OrderSelect.RELEASE
        }
        if(lastButtonState != orderSelect) {
            refreshButtonState()
            refreshList()
        }
    }

    override fun displayFavorites(viewModel: ListUserCases.FavoritesList.ViewModel) {
        if(viewModel.items.count() > 0) {
            list_empty_text.visibility = View.GONE
            listAdapter.passViewModel(viewModel, orderSelect == OrderSelect.RATING)
        } else {
            list_empty_text.visibility = View.VISIBLE
            listAdapter.clear()
        }
    }

    private fun setListeners() {
        list_button_title.setOnClickListener(this)
        list_button_rating.setOnClickListener(this)
        list_button_release.setOnClickListener(this)

        listAdapter.itemClick = { index ->
            dataStore?.let { dataStore ->
                val intent = DetailsContainerActivity.getNewIntentDetailed(context!!, dataStore.moviesFavorites, index)
                startActivity(intent)
            }
        }
    }

    private fun prepareRecyclerView(){
        val viewManager = LinearLayoutManager(context)

        list_recycler_view.apply {
            layoutManager = viewManager
            adapter = listAdapter
        }
    }

    private fun refreshList(){
        val request = when(orderSelect) {
            MyListFragment.OrderSelect.TITLE -> ListUserCases.FavoritesList.Request(FavoritesProvider.OrderByEnum.TITLE)
            MyListFragment.OrderSelect.RATING -> ListUserCases.FavoritesList.Request(FavoritesProvider.OrderByEnum.RATING)
            MyListFragment.OrderSelect.RELEASE -> ListUserCases.FavoritesList.Request(FavoritesProvider.OrderByEnum.RELEASE)
        }
        interactor.requestFavorites(request)
    }

    private fun refreshButtonState(){
        when(orderSelect) {
            OrderSelect.TITLE -> {
                displayAsSelected(list_button_title)
                displayAsUnselected(list_button_rating)
                displayAsUnselected(list_button_release)
            }
            OrderSelect.RATING -> {
                displayAsUnselected(list_button_title)
                displayAsSelected(list_button_rating)
                displayAsUnselected(list_button_release)
            }
            OrderSelect.RELEASE -> {
                displayAsUnselected(list_button_title)
                displayAsUnselected(list_button_rating)
                displayAsSelected(list_button_release)
            }
        }
    }


    private fun displayAsSelected(button: Button){
        button.setBackgroundResource(R.drawable.bg_button_selected)
        button.setTextColor(resources.getColor(R.color.white))
        button.typeface = ResourcesCompat.getFont(context!!, R.font.comfortaa_bold)
    }

    private fun displayAsUnselected(button: Button){
        button.setBackgroundResource(R.drawable.bg_button_border)
        button.setTextColor(resources.getColor(R.color.reddish))
        button.typeface = ResourcesCompat.getFont(context!!, R.font.comfortaa_regular)
    }
}