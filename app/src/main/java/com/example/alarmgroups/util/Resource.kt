package com.example.alarmgroups.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(message: String? = null, data: T? = null) : Resource<T>(message = message, data = data)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>()
}