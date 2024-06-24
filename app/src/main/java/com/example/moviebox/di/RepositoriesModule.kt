package com.example.moviebox.di

import com.example.moviebox.data.repositories.MovieDescriptionRepositoryImpl
import com.example.moviebox.domain.repositories.MovieDescriptionRepository
import com.example.moviebox.data.repositories.PopularMoviesRepositoryImpl
import com.example.moviebox.domain.repositories.PopularMoviesRepository
import com.example.moviebox.data.repositories.SearchPopularMoviesRepositoryImpl
import com.example.moviebox.domain.repositories.SearchPopularMoviesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun providePopularMoviesRepository(repositoryImpl: PopularMoviesRepositoryImpl):
            PopularMoviesRepository = repositoryImpl

    @Provides
    fun provideMovieDescriptionRepository(repositoryImpl: MovieDescriptionRepositoryImpl):
            MovieDescriptionRepository = repositoryImpl

    @Provides
    fun provideSearchMoviesRepository(repositoryImpl: SearchPopularMoviesRepositoryImpl):
            SearchPopularMoviesRepository = repositoryImpl
}