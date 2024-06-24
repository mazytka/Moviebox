package com.example.moviebox.domain.interactors

import com.example.moviebox.data.models.MovieModel
import com.example.moviebox.domain.repositories.PopularMoviesRepository
import javax.inject.Inject

class PopularMoviesInteractor @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository
) {
    suspend fun getPopularMovies() = popularMoviesRepository.getPopularMovies()
    suspend fun insertMovie(movie: MovieModel) = popularMoviesRepository.insertMovie(movie)
    suspend fun removeMovie(movie: MovieModel) = popularMoviesRepository.removeMovie(movie)
    suspend fun checkIfFavourite(movieId: Int) = popularMoviesRepository.isFavourite(movieId)
}