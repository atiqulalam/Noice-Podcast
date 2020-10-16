package com.noice

import android.app.Application
import android.content.Context
import com.noice.rest.CustomError
import com.noice.rest.RestInterface
import com.noice.utils.NetworkUtils
import com.noice.utils.NoicePref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class NoiceApplication : Application() {

    companion object {
        lateinit var sharedNoicePref: NoicePref
        private lateinit var ctx: Context

        fun <T> doServerCall(
            method: suspend () -> Response<T>?,
            restInterface: RestInterface<T>
        ): Job? {

            if (!NetworkUtils.isNetworkConnected(ctx)) {
                restInterface.onCustomError(
                    CustomError(
                        ctx.resources?.getString(R.string.network_error) ?: "",
                        CustomError.NO_INTERNET
                    )
                )
                return null
            }
            return CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = method.invoke()

                    if (response?.isSuccessful == true && response.body() != null) {
                        restInterface.onSuccess(response.body()!!)
                    } else {
                        handleError(response, restInterface)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    restInterface.onError(e)
                }
            }
        }

        private fun <T> handleError(response: Response<T>?, restInterface: RestInterface<T>) {
            val stringJson = response?.errorBody()?.string()

            restInterface.onCustomError(
                CustomError(
                    ctx.resources?.getString(R.string.generic_error) ?: "",
                    response?.code() ?: -1,
                    stringJson
                )
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        sharedNoicePref = NoicePref(ctx)
    }
}