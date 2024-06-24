package com.example.moviebox.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviebox.data.models.CountryModel
import com.example.moviebox.data.models.GenreModel
import com.example.moviebox.data.utils.converters.CountryModelTypeConverter
import com.example.moviebox.data.utils.converters.GenreModelTypeConverter

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: String,
    val description: String?,
    val isFavorite: Boolean,
    @TypeConverters(CountryModelTypeConverter::class) val countries: List<CountryModel>,
    @TypeConverters(GenreModelTypeConverter::class) val genres: List<GenreModel>
)
