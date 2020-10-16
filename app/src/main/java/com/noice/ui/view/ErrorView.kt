package com.noice.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.noice.R
import kotlinx.android.synthetic.main.error_view.view.*

class ErrorView : FrameLayout {

    private lateinit var ctx: Context

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        ctx = context
        inflate(context, R.layout.error_view, this)
        visibility = GONE
    }

    fun setListener(errorListener: ErrorCallBack) {
        txtRetry.setOnClickListener {
            errorListener.onRetryClick()
        }
    }

    fun setErrorMessage(message: String? = ctx.resources.getString(R.string.generic_error)) {
        errorViewMainLayout.visibility = VISIBLE
        visibility = VISIBLE
        errorLayout.visibility = VISIBLE
        progressBar.visibility = GONE
        txtMessage.text = message
        txtRetry.visibility = VISIBLE
    }

    fun showLoading() {
        errorLayout.visibility = GONE
        progressBar.visibility = VISIBLE
        errorViewMainLayout.visibility = VISIBLE
        visibility = VISIBLE
    }

    fun hideLoading() {
        errorViewMainLayout.visibility = GONE
        visibility = GONE
        errorLayout.visibility = GONE
        progressBar.visibility = GONE
    }

    fun setErrorBackGround(@ColorRes color: Int) {
        errorViewMainLayout.setBackgroundColor(ContextCompat.getColor(ctx, color))
    }

    fun showEmptyView() {
        errorViewMainLayout.visibility = VISIBLE
        visibility = VISIBLE
        errorLayout.visibility = VISIBLE
        progressBar.visibility = GONE
        txtMessage.text = ctx.getString(R.string.data_empty)
        txtRetry.visibility = GONE
    }

    interface ErrorCallBack {
        fun onRetryClick()
    }
}