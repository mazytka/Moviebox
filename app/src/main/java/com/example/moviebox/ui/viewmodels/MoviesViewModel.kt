package com.example.moviebox.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.models.MovieModel
import com.example.moviebox.data.utils.responses.StatusResponse
import com.example.moviebox.domain.interactors.FavoriteInteractor
import com.example.moviebox.domain.interactors.PopularMoviesInteractor
import com.example.moviebox.domain.interactors.SearchPopularMoviesInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val popularMoviesInteractor: PopularMoviesInteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val searchInteractor: SearchPopularMoviesInteractor
) : ViewModel() {

    private var _movieModelResponse = MutableLiveData<StatusResponse<List<MovieModel>>>()
    val movieModelResponse: LiveData<StatusResponse<List<MovieModel>>> = _movieModelResponse

    private var _isFavourite = MutableLiveData<Pair<Int, Boolean>?>()
    val isFavourite: LiveData<Pair<Int, Boolean>?> = _isFavourite

    private val _favoriteResponse = MutableLiveData<StatusResponse<List<MovieModel>>>()
    val favoriteResponse: LiveData<StatusResponse<List<MovieModel>>> = _favoriteResponse

    private var _searchedMovieModelResponse = MutableLiveData<StatusResponse<List<MovieModel>>>()
    val searchedMovieModelResponse: LiveData<StatusResponse<List<MovieModel>>> = _searchedMovieModelResponse

    init {
        fetchAllMovies()
        fetchFavoriteMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            popularMoviesInteractor.getPopularMovies().collect {
                _movieModelResponse.postValue(it)
            }
        }
    }

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            favoriteInteractor.getAllFavoriteMovies().collect {
                _favoriteResponse.postValue(it)
            }
        }
    }

    fun fetchSearchedMovies(keyword: String) {
        viewModelScope.launch {
            searchInteractor.getSearchedMovies(keyword).collect {
                _searchedMovieModelResponse.postValue(it)
            }
        }
    }

    fun checkIfFavourite(movie: MovieModel): Boolean {
        viewModelScope.launch {
            val isFav = popularMoviesInteractor.checkIfFavourite(movie.filmId)

            if (isFav) {
                popularMoviesInteractor.removeMovie(movie)
            } else {
                popularMoviesInteractor.insertMovie(movie)
            }
            _isFavourite.postValue(Pair(movie.filmId, !isFav))
        }

        return true
    }

    fun removeMovieFromFavorite(movie: MovieModel) {
        viewModelScope.launch {
            favoriteInteractor.removeMovie(movie)
            _isFavourite.postValue(Pair(movie.filmId, false))
            updateMovieStatus(movie.filmId, false)
        }
    }

    fun updateMovieStatus(movieId: Int, isFav: Boolean) {
        when(_movieModelResponse.value as StatusResponse<List<MovieModel>>) {
            is StatusResponse.Success -> {
                (_movieModelResponse.value as StatusResponse.Success<List<MovieModel>>)
                    .data.first {
                        it.filmId == movieId
                    }.isFavorite = isFav
            }
            else -> {}
        }
    }

    fun updateSearchMovieStatus(movieId: Int, isFav: Boolean) {
        when(_searchedMovieModelResponse.value as StatusResponse<List<MovieModel>>) {
            is StatusResponse.Success -> {
                (_searchedMovieModelResponse.value as StatusResponse.Success<List<MovieModel>>)
                    .data.first {
                        it.filmId == movieId
                    }.isFavorite = isFav
            }
            else -> {}
        }
    }

    fun clearFavStatus() {
        _isFavourite.postValue(null)
    }
}