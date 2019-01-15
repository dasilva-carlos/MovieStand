package com.dasilvacarlos.moviesstand.presentation.main.fragments.search

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
import android.view.KeyEvent
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import com.dasilvacarlos.moviesstand.presentation.main.NavigationView
import java.util.concurrent.Semaphore


class SearchFragment: GenericFragment(), SearchView {

    companion object {
        val animationDuration: Long = 600
    }

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareListeners()
        prepareDefaultAnimationValues()
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
            search_parent_layout.requestFocus()
            hideKeyboard()
        }

        search_edit_text.onKeyPreImeListener = { _, event ->
            if (event?.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                search_parent_layout.requestFocus()
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    fun prepareDefaultAnimationValues() {
        searchViewLayoutParams = RelativeLayout.LayoutParams(search_wrapper_layout.layoutParams as RelativeLayout.LayoutParams)
        roundBorderSize = resources.getDimension(R.dimen.round_border)
        semiTransparentAlpha = Color.alpha(resources.getColor(R.color.white))
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
                (activity as? NavigationView)?.hideBottomBar()
            }
            duration = animationDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    search_semi_transparent_view.visibility = View.VISIBLE
                    (activity as? NavigationView)?.hideBottomBar()
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
            duration = animationDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    search_semi_transparent_view.visibility = View.GONE
                    (activity as? NavigationView)?.showBottomBar()
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

    private fun calculateIntermediateValue(start: Int, end: Int, percentage: Int): Int {
        return start - (start - end) * percentage / 100
    }

    private fun calculateIntermediateValue(start: Float, end: Float, percentage: Float): Float {
        return start - (start - end) * percentage / 100.0f
    }
}

