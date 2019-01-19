package com.dasilvacarlos.moviesstand.presentation.main_navigation

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import com.dasilvacarlos.moviesstand.R
import com.dasilvacarlos.moviesstand.presentation.generic.GenericActivity
import com.dasilvacarlos.moviesstand.presentation.generic.GenericFragment
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : GenericActivity(), NavigationViewLogic, BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        val animationDuration: Long = 800
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setupBottomBar()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigation_search -> {
                setBottomBarTint(NavigationHelper.searchColor)
                setFragment(NavigationHelper.searchFragment)
            }

            R.id.navigation_favorites -> {
                setBottomBarTint(NavigationHelper.favoritesColor)
                setFragment(NavigationHelper.favoritesFragment)
            }

            R.id.navigation_list -> {
                setBottomBarTint(NavigationHelper.myListColor)
                setFragment(NavigationHelper.myListFragment)
            }
        }
        return true
    }

    override fun hideBottomBar() {
        navigation_bottom_bar.visibility = View.GONE
    }

    override fun showBottomBar() {
        navigation_bottom_bar.visibility = View.VISIBLE
    }

    private fun setupBottomBar() {
        navigation_bottom_bar.setOnNavigationItemSelectedListener(this)
        navigation_bottom_bar.selectedItemId = R.id.navigation_search
    }

    private fun setFragment(fragment: GenericFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navigation_container, fragment)
        transaction.commit()
    }

    private fun setBottomBarTint(colorStateList: ColorStateList) {
        navigation_bottom_bar.itemIconTintList = colorStateList
        navigation_bottom_bar.itemTextColor = colorStateList
    }
}
