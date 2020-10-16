package com.noice.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.noice.R

class CustomToolbar : Toolbar {

    private lateinit var ctx: Context

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context){
        ctx = context
        inflate(context, R.layout.toolbar, this)
    }
}