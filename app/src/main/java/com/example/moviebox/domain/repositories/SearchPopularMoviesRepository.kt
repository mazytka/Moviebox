package com.example.moviebox.domain.repositories

import com.example.moviebox.data.models.MovieModel
import com.example.moviebox.data.utils.responses.StatusResponse
import kotlinx.coroutines.flow.Flow

interface SearchPopularMoviesRepository {
    suspend fun getSearchedMovies(keyword: String): Flow<StatusResponse<List<MovieModel>>>
    suspend fun isFavourite(movieId: Int): Boolean
}