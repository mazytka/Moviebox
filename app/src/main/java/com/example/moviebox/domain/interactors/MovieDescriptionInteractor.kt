package com.example.moviebox.domain.interactors

import com.example.moviebox.domain.repositories.MovieDescriptionRepository
import javax.inject.Inject

class MovieDescriptionInteractor @Inject constructor(
    private val mainDescriptionRepository: MovieDescriptionRepository
) {

    suspend fun getMovieDescription(movieId: Int) =
        mainDescriptionRepository.getMovieDescription(movieId)
}