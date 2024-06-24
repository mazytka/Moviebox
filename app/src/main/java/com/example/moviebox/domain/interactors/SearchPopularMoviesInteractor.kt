package com.example.moviebox.domain.interactors

import com.example.moviebox.domain.repositories.SearchPopularMoviesRepository
import javax.inject.Inject

class SearchPopularMoviesInteractor @Inject constructor(
    private val searchMovieRepository: SearchPopularMoviesRepository
) {
    suspend fun getSearchedMovies(keyword: String) =
        searchMovieRepository.getSearchedMovies(keyword)
}