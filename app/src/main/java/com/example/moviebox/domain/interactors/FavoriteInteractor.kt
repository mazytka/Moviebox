package com.example.moviebox.domain.interactors

import com.example.moviebox.data.models.MovieModel
import com.example.moviebox.data.utils.responses.StatusResponse
import com.example.moviebox.domain.repositories.PopularMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository
) {
    suspend fun removeMovie(movie: MovieModel) = popularMoviesRepository.removeMovie(movie)
    suspend fun getAllFavoriteMovies(): Flow<StatusResponse<List<MovieModel>>> =
        popularMoviesRepository.getAllFavoriteMovies()
}