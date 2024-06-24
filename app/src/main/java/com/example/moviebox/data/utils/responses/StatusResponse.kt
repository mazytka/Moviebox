package com.example.moviebox.data.utils.responses

sealed class StatusResponse<T> {
    data class Loading<T>(val isLoading: Boolean) : StatusResponse<T>()
    data class Success<T>(val data: T) : StatusResponse<T>()
    data class Failure<T>(val errorMessage: String) : StatusResponse<T>()
}