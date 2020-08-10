package com.enplus.network

interface HttpCallback<T> {
    fun onSuccess(result: T?)
    fun onFailure(e: Exception?)
}