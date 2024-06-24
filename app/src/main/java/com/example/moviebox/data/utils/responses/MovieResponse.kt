package com.example.moviebox.data.utils.responses

import com.example.moviebox.data.models.MovieModel

data class MovieResponse(
    val pagesCount: Int,
    val films: List<MovieModel>
)