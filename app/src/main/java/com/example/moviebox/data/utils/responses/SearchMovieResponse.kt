package com.example.moviebox.data.utils.responses

import com.example.moviebox.data.models.MovieModel

data class SearchMovieResponse(
    val keyword: String,
    val pagesCount: Int,
    val films: List<MovieModel>,
    val searchFilmsCountResult: Int
)