package com.example.moviebox.data.repositories

import com.example.moviebox.data.api.MovieApiService
import com.example.moviebox.data.utils.converters.toMovieDescriptionModel
import com.example.moviebox.data.utils.responses.StatusResponse
import com.example.moviebox.domain.repositories.MovieDescriptionRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDescriptionRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieDescriptionRepository {

    override suspend fun getMovieDescription(movieId: Int) =
        flow {
            emit(StatusResponse.Loading(true))
            val response = apiService.getMovieDescription(movieId)
            emit(StatusResponse.Success(response.toMovieDescriptionModel()))
        }.catch { e ->
            emit(StatusResponse.Failure(e.message ?: "Unknown error has occurred"))
        }
}