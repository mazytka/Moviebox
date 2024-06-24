package com.example.moviebox.domain.repositories

import com.example.moviebox.data.models.MovieModel
import com.example.moviebox.data.utils.responses.StatusResponse
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {
    suspend fun getPopularMovies(): Flow<StatusResponse<List<MovieModel>>>
    suspend fun insertMovie(movie: MovieModel)
    suspend fun removeMovie(movie: MovieModel)
    suspend fun getAllFavoriteMovies(): Flow<StatusResponse<List<MovieModel>>>
    suspend fun isFavourite(movieId: Int): Boolean
}