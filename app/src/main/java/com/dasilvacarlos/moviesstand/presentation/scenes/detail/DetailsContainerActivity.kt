package com.dasilvacarlos.moviesstand.presentation.scenes.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.data.models.MovieModel
import com.dasilvacarlos.moviesstand.data.models.ResumedMovieModel
import com.dasilvacarlos.moviesstand.presentation.generic.GenericActivity
import com.dasilvacarlos.moviesstand.presentation.generic.MovieStandApplication
import kotlinx.android.synthetic.main.activity_details_container.*
import java.io.Serializable
import java.lang.Math.abs


class DetailsContainerActivity: GenericActivity() {

    companion object {
        private const val MOVIE_LIST_KEY = "MovieListKey"
        private const val START_INDEX_KEY = "StartIndexKey"

        fun getNewIntent(origin: Context, movies: ArrayList<ResumedMovieModel>, startIndex: Int): Intent {
            val intent = Intent(origin, DetailsContainerActivity::class.java)
            intent.putExtra(MOVIE_LIST_KEY, movies)
            intent.putExtra(START_INDEX_KEY, startIndex)
            return intent
        }

        fun getNewIntentDetailed(origin: Context, movies: ArrayList<MovieModel>, startIndex: Int): Intent {
            val intent = Intent(origin, DetailsContainerActivity::class.java)
            intent.putExtra(MOVIE_LIST_KEY, movies)
            intent.putExtra(START_INDEX_KEY, startIndex)
            return intent
        }
    }

    private var movieList:  ArrayList<Serializable> = arrayListOf()
    private var startIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_container)
        setupBundleInformation()
        setupViewPager()
        setupListeners()
    }

    private fun setupBundleInformation() {
        val bundle = intent.extras
        (bundle.getSerializable(MOVIE_LIST_KEY) as? ArrayList<Serializable>)?.let {
            movieList = it
        }
        startIndex = bundle.getInt(START_INDEX_KEY)
    }

    private fun setupListeners() {
        details_activity_main_view.setOnClickListener{finish()}
    }

    private fun setupViewPager() {
        details_activity_view_pager.adapter = DetailsPagerAdapter(supportFragmentManager, movieList)
        details_activity_view_pager.offscreenPageLimit = 5
        details_activity_view_pager.currentItem = startIndex

        val margin = MovieStandApplication.instance.applicationContext.resources.getDimension(R.dimen.margin_big).toInt()
        details_activity_view_pager.setPadding(margin, 0, margin, 0)
        details_activity_view_pager.clipToPadding = false
        details_activity_view_pager.setPageTransformer(false, PageLowerTransformer())
    }

    private class DetailsPagerAdapter(fragmentManager: FragmentManager,
                                      val information: ArrayList<Serializable>) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            val information = information[position]
            return when(information) {
                is MovieModel -> DetailsFragment.getNewInstance(information)
                is ResumedMovieModel -> DetailsFragment.getNewInstance(information)
                else -> DetailsFragment()
            }
        }

        override fun getCount(): Int {
            return information.count()
        }
    }

    private class PageLowerTransformer: ViewPager.PageTransformer {

        val yTranslationMultiplier = MovieStandApplication.instance.applicationContext.resources.getDimension(R.dimen.margin_extra_big)

        override fun transformPage(page: View, position: Float) {
            page.translationY = yTranslationMultiplier * abs(position)
        }
    }
}