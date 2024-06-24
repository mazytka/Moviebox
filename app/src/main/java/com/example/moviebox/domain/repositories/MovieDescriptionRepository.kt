package com.example.moviebox.domain.repositories

import com.example.moviebox.data.models.MovieDescriptionModel
import com.example.moviebox.data.utils.responses.StatusResponse
import kotlinx.coroutines.flow.Flow

interface MovieDescriptionRepository {
    suspend fun getMovieDescription(movieId: Int): Flow<StatusResponse<MovieDescriptionModel>>
}