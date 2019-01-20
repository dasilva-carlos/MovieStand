package com.dasilvacarlos.moviesstand.presentation.library.helpers

import android.view.animation.Animation
import android.view.animation.Animation.REVERSE
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.view.animation.ScaleAnimation
import com.dasilvacarlos.moviesstand.R


class FavoriteStarHelper(val image: ImageView) {

    var isFilled: Boolean = false
    var spinningAnimation: Animation = buildSpinningAnimation()

    init {
        image.setImageResource(R.drawable.ic_star_empty)
    }

    fun animateWaitingForResult() {
        image.startAnimation(spinningAnimation)
    }

    fun statusChange(isSaved: Boolean, animated: Boolean = false) {
        isFilled = isSaved

        image.clearAnimation()

        val resource = if(isSaved) R.drawable.ic_star else R.drawable.ic_star_empty
        image.setImageResource(resource)

        if (animated) {
            val animation = ScaleAnimation(
                    1f, 1.2f,
                    1f, 1.2f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f)
            animation.duration = 300
            animation.repeatCount = 1
            animation.repeatMode = REVERSE

            image.startAnimation(animation)
        }
    }

    private fun buildSpinningAnimation(): Animation {
        val animation =  RotateAnimation(
                0.0F,
                36.0F,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f)
        animation.duration = 100
        animation.repeatCount = Animation.INFINITE
        return animation
    }
}