package com.example.moviebox.data.room.daos

import androidx.room.*
import com.example.moviebox.data.room.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movies WHERE filmId = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Insert
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE filmId = :filmId)")
    suspend fun isFavourite(filmId: Int): Boolean
}
