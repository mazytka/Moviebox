package com.example.moviebox.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviebox.data.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    @Singleton
    abstract fun provideViewModelFactory(factory: ViewModelFactory<ViewModel>): ViewModelProvider.Factory
}