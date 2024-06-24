package com.example.moviebox.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviebox.data.room.daos.MovieDao
import com.example.moviebox.data.utils.converters.CountryModelTypeConverter
import com.example.moviebox.data.utils.converters.GenreModelTypeConverter
import com.example.moviebox.data.room.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(CountryModelTypeConverter::class, GenreModelTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
