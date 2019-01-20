package com.dasilvacarlos.moviesstand.presentation.scenes.search

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.presentation.generic.GenericFragment
import kotlinx.android.synthetic.main.fragment_search.*
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.RelativeLayout
import com.dasilvacarlos.moviesstand.data.generics.ServiceError
import com.dasilvacarlos.moviesstand.domain.app.search.*
import com.dasilvacarlos.moviesstand.presentation.generic.MovieStandApplication
import com.dasilvacarlos.moviesstand.presentation.main_navigation.NavigationViewLogic
import com.dasilvacarlos.moviesstand.presentation.scenes.detail.DetailsContainerActivity
import com.dasilvacarlos.moviesstand.presentation.scenes.search.adapter.SearchListAdapter


class SearchFragment: GenericFragment(), SearchViewLogic {

    companion object {
        const val ANIMATION_DURATION: Long = 600
    }

    private val interactor: SearchInteractorLogic = SearchInteractor(this)
    private val dataStore: SearchDataStore? by lazy { interactor as? SearchDataStore }

    private var searchViewLayoutParams: RelativeLayout.LayoutParams? = null
    private var roundBorderSize: Float? = null
    private var semiTransparentAlpha: Int? = null
    private val gradientDrawable: GradientDrawable by lazy {
        GradientDrawable().apply {
            setColor(resources.getColor(R.color.white))
        }
    }

    private var isSearchDisplayed = false
    private var lastAnimator: Animator? = null

    private val searchListAdapter: SearchListAdapter by lazy { SearchListAdapter(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareDefaultAnimationValues()
        prepareSearchList()
        prepareListeners()
    }

    override fun displaySearchResult(viewModel: SearchUserCases.SearchForMovieTitle.ViewModel) {
        searchListAdapter.passViewModel(viewModel)
    }

    override fun displayFavoriteChange(viewModel: SearchUserCases.ChangeFavorite.ViewModel) {
        searchListAdapter.updateIsFavorite(viewModel)
        if(!viewModel.isSuccess) {
            displayError(getString(R.string.details_error_save))
        }
    }

    override fun displayError(request: Any, error: ServiceError) {
        when (request){
            is SearchUserCases.SearchForMovieTitle.Request -> searchListAdapter.setErrorMessage(error.getDescription(context ?: MovieStandApplication.instance.applicationContext))
            else -> super.displayError(request, error)
        }
    }

    private fun prepareListeners(){
        search_edit_text.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                displaySearch()
            } else {
                hideSearch()
            }
        }

        search_semi_transparent_view.setOnClickListener {
            dispatchHideSearch()
        }

        search_edit_text.onKeyPreImeListener = { _, event ->
            (event?.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP).apply {
                if(this) {
                    dispatchHideSearch()
                }
            }
        }

        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val titleQuery = s.toString()
                requestQuery(titleQuery)
            }
        })

        searchListAdapter.itemClick = { index ->
            dataStore?.let { dataStore ->
                val intent = DetailsContainerActivity.getNewIntent(context!!, dataStore.moviesSearched, index)
                startActivity(intent)
            }
        }

        searchListAdapter.itemStarClick = { index, save ->
            interactor.requestFavoriteChange(SearchUserCases.ChangeFavorite
                    .Request(index = index, isFavorite = save))
        }
    }

    private fun requestQuery(titleQuery: String) {
        val request = SearchUserCases.SearchForMovieTitle.Request(titleQuery)
        searchListAdapter.clearItems()
        searchListAdapter.setErrorMessage(context!!.getString(R.string.search_searching))
        interactor.searchForMovieTitle(request)
    }

    private fun prepareDefaultAnimationValues() {
        searchViewLayoutParams = RelativeLayout.LayoutParams(search_wrapper_layout.layoutParams as RelativeLayout.LayoutParams)
        roundBorderSize = resources.getDimension(R.dimen.round_border)
        semiTransparentAlpha = Color.alpha(resources.getColor(R.color.white))
    }

    private fun dispatchHideSearch() {
        search_parent_layout.requestFocus()
        hideKeyboard()
    }

    private fun displaySearch() {
        isSearchDisplayed = true
        lastAnimator?.end()
        lastAnimator = ValueAnimator.ofInt(0, 100).apply {
            addUpdateListener { valueAnimator ->
                val animationPercentage = valueAnimator.animatedValue as Int
                setupSearchMargins(animationPercentage)
                setupRoundCorners(animationPercentage)
                setupSemiTransparentViewVisibility(animationPercentage)
                (activity as? NavigationViewLogic)?.hideBottomBar()
            }
            duration = ANIMATION_DURATION
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    search_semi_transparent_view.visibility = View.VISIBLE
                    (activity as? NavigationViewLogic)?.hideBottomBar()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    search_recycler_view.visibility = View.VISIBLE
                }
            })
        }
        lastAnimator?.start()
    }

    private fun hideSearch() {
        isSearchDisplayed = false
        lastAnimator?.end()
        lastAnimator = ValueAnimator.ofInt(100, 0).apply {
            addUpdateListener { valueAnimator ->
                val animationPercentage = valueAnimator.animatedValue as Int
                setupSearchMargins(animationPercentage)
                setupRoundCorners(animationPercentage)
                setupSemiTransparentViewVisibility(animationPercentage)
            }
            duration = ANIMATION_DURATION
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    search_recycler_view.visibility = View.GONE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    search_semi_transparent_view.visibility = View.GONE
                    (activity as? NavigationViewLogic)?.showBottomBar()
                }
            })
        }
        lastAnimator?.start()
    }

    private fun setupSearchMargins(percentage: Int) {
        searchViewLayoutParams?.let {
            val params =  search_wrapper_layout.layoutParams as RelativeLayout.LayoutParams
            params.topMargin = calculateIntermediateValue(it.topMargin, 0, percentage)
            params.leftMargin = calculateIntermediateValue(it.leftMargin, 0, percentage)
            params.rightMargin = calculateIntermediateValue(it.rightMargin, 0, percentage)
            search_wrapper_layout.layoutParams = params
        }
    }

    private fun setupRoundCorners(percentage: Int) {
        roundBorderSize?.let{
            gradientDrawable.cornerRadius = calculateIntermediateValue(it, 0.0f, percentage.toFloat())
            search_wrapper_layout.background = gradientDrawable
        }
    }

    private fun setupSemiTransparentViewVisibility(percentage: Int) {
        semiTransparentAlpha?.let {
            search_semi_transparent_view.background.alpha = calculateIntermediateValue(0, it, percentage)
        }
    }

    private fun prepareSearchList() {
        val viewManager = LinearLayoutManager(context)

        search_recycler_view.apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = searchListAdapter
        }
    }

    private fun calculateIntermediateValue(start: Int, end: Int, percentage: Int): Int {
        return start - (start - end) * percentage / 100
    }

    private fun calculateIntermediateValue(start: Float, end: Float, percentage: Float): Float {
        return start - (start - end) * percentage / 100.0f
    }
}

