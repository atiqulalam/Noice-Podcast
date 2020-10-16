package com.noice.rest

interface RestInterface<T> {
    fun onCustomError(e: CustomError)

    fun onError(e: Throwable)

    fun onSuccess(data: T )
}