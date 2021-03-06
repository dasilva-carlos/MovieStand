package com.dasilvacarlos.moviesstand.presentation.library.custom_views

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.KeyEvent


class ExtendedEditText: AppCompatEditText {

    var onKeyPreImeListener: ((Int, KeyEvent?) -> Boolean)? = null

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        onKeyPreImeListener?.invoke(keyCode, event)?.let {
            return it || super.onKeyPreIme(keyCode, event)
        }

        return super.onKeyPreIme(keyCode, event)
    }
}