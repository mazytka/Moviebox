package com.example.moviebox.data.utils.converters

import com.example.moviebox.data.models.MovieDescriptionModel
import com.example.moviebox.data.models.MovieModel
import com.example.moviebox.data.room.entities.MovieEntity
import com.example.moviebox.data.utils.responses.MovieDescriptionResponse

fun MovieEntity.toModelFavorite(): MovieModel {
    return MovieModel(
        filmId = this.filmId,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        year = this.year,
        genres = this.genres,
        posterUrl = this.posterUrl,
        posterUrlPreview = this.posterUrlPreview,
        description = this.description,
        countries = this.countries,
        isFavorite = true
    )
}

fun MovieModel.toMovieEntity(): MovieEntity {
    return MovieEntity(
        filmId = this.filmId,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        year = this.year,
        countries = this.countries,
        genres = this.genres,
        posterUrl = this.posterUrl,
        posterUrlPreview = this.posterUrlPreview,
        description = this.description,
        isFavorite = this.isFavorite
    )
}

fun MovieDescriptionResponse.toMovieDescriptionModel(): MovieDescriptionModel {
    return MovieDescriptionModel(
        this.filmId,
        this.nameRu,
        this.nameEn,
        this.posterUrl,
        this.webUrl,
        this.year,
        this.description,
        this.countries,
        this.genres
    )
}