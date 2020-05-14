package com.zhou.baselibrary.network

interface HttpCallback<T> {
    fun onSuccess(result: T?)
    fun onFailure(e: Exception?)
}