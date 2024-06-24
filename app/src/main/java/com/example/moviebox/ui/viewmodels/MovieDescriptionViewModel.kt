package com.example.moviebox.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.data.models.MovieDescriptionModel
import com.example.moviebox.data.utils.responses.StatusResponse
import com.example.moviebox.domain.interactors.MovieDescriptionInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDescriptionViewModel @Inject constructor(
    private val interactor: MovieDescriptionInteractor
) : ViewModel() {

    private var _movieDescriptionResponse =
        MutableLiveData<StatusResponse<MovieDescriptionModel>>()
    val movieDescriptionResponse: LiveData<StatusResponse<MovieDescriptionModel>> =
        _movieDescriptionResponse

    fun fetchMovieDescription(movieId: Int) {
        viewModelScope.launch {
            interactor.getMovieDescription(movieId).collect {
                _movieDescriptionResponse.postValue(it)
            }
        }
    }

}