package com.noice.utils



data class Resource<out T>(val status: ResponseStatus, val data: T?, val message: String?) {
    companion object {

        const val GENERIC_ERROR = "Something went wrong. Please try again later"

        fun <T> success(data: T?): Resource<T> {
            return Resource(
                ResponseStatus.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(
                ResponseStatus.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                ResponseStatus.LOADING,
                data,
                null
            )
        }
    }
}