package com.example.moviebox.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.moviebox.MovieBox
import com.example.moviebox.data.models.MovieModel
import com.example.moviebox.data.utils.responses.StatusResponse
import com.example.moviebox.data.utils.ViewModelFactory
import com.example.moviebox.databinding.FragmentPopularBinding
import com.example.moviebox.ui.adapters.MoviesAdapter
import com.example.moviebox.ui.viewmodels.MoviesViewModel
import javax.inject.Inject

class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var moviesAdapter: MoviesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<MoviesViewModel>
    private val viewModel by activityViewModels<MoviesViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieBox).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater)

        moviesAdapter = MoviesAdapter(
            openMovieDescription = this::openMovieDescription,
            addMovieToFavorites = this::checkIfFavourite
        )

        binding.rvMovies.apply {
            adapter = moviesAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(PopularFragmentDirections.actionPopularFragmentToFavoriteFragment())
        }

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(PopularFragmentDirections.actionPopularFragmentToSearchFragment())
        }

        viewModel.movieModelResponse.observe(viewLifecycleOwner) {
            when (it) {
                is StatusResponse.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }

                is StatusResponse.Failure -> {
                    if (it.errorMessage == "Unable to resolve host \"kinopoiskapiunofficial.tech\": No address associated with hostname") {
                        binding.imgError.visibility = View.VISIBLE
                        binding.txtErrorUp.visibility = View.VISIBLE
                        binding.txtErrorDown.visibility = View.VISIBLE
                    } else Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                    binding.progressBar.isVisible = false
                }

                is StatusResponse.Success -> {
                    moviesAdapter.updateMovies(it.data)
                    binding.progressBar.isVisible = false
                }
            }

        }
        viewModel.isFavourite.observe(viewLifecycleOwner) {
            it?.let { pair ->
                val movie = moviesAdapter.moviesList.first { movie ->
                    movie.filmId == pair.first
                }
                val index = moviesAdapter.moviesList.indexOf(movie)
                val movies = moviesAdapter.moviesList.toMutableList()
                movies.removeAt(index)
                movies.add(index, movie.copy().apply { isFavorite = pair.second })
                moviesAdapter.updateMovies(movies)
                viewModel.updateMovieStatus(pair.first, pair.second)
                viewModel.clearFavStatus()
            }
        }
    }

    private fun openMovieDescription(movieId: String) {
        findNavController().navigate(
            PopularFragmentDirections.actionPopularFragmentToMovieDescriptionFragment2(
                movieId
            )
        )
    }

    private fun checkIfFavourite(movie: MovieModel): Boolean {
        return viewModel.checkIfFavourite(movie)
    }
}