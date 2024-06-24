package com.example.moviebox.data.utils.responses

import com.example.moviebox.data.models.CountryModel
import com.example.moviebox.data.models.GenreModel

data class MovieDescriptionResponse(
    val filmId: Int,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val posterUrl: String,
    val webUrl: String,
    val year: String,
    val description: String? = null,
    val countries: List<CountryModel>,
    val genres: List<GenreModel>
)