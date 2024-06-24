package com.example.moviebox.di

import android.app.Application
import com.example.moviebox.ui.fragments.FavoriteFragment
import com.example.moviebox.ui.fragments.MovieDescriptionFragment
import com.example.moviebox.ui.fragments.PopularFragment
import com.example.moviebox.ui.fragments.SearchPopularMoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class, RepositoriesModule::class, ViewModelModule::class,
        RoomModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(fragment: PopularFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: SearchPopularMoviesFragment)
    fun inject(fragment: MovieDescriptionFragment)
}