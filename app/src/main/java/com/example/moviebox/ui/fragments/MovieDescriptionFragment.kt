package com.example.moviebox.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviebox.MovieBox
import com.example.moviebox.data.utils.responses.StatusResponse
import com.example.moviebox.data.utils.ViewModelFactory
import com.example.moviebox.databinding.FragmentMovieDescriptionBinding
import com.example.moviebox.ui.viewmodels.MovieDescriptionViewModel
import javax.inject.Inject

class MovieDescriptionFragment : Fragment() {

    private lateinit var binding: FragmentMovieDescriptionBinding
    private lateinit var movieId: String

    private val args: MovieDescriptionFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<MovieDescriptionViewModel>
    private lateinit var viewModel: MovieDescriptionViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MovieBox).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDescriptionBinding.inflate(inflater)
        movieId = args.movieId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = viewModelFactory.getViewModel(this)
//        Log.d("MovieDescription", movieId)
        viewModel.fetchMovieDescription(movieId.toInt())

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.movieDescriptionResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StatusResponse.Loading -> {
                    binding.progressBar.isVisible = response.isLoading
                }

                is StatusResponse.Failure -> {
                    if (response.errorMessage == "Unable to resolve host \"kinopoiskapiunofficial.tech\": No address associated with hostname") {
                        binding.tvMovieTitle.visibility = View.GONE
                        binding.tvMovieCountriesTitle.visibility = View.GONE
                        binding.tvMovieGenreTitle.visibility = View.GONE
                        binding.tvMovieYearTitle.visibility = View.GONE
                        binding.imgError.visibility = View.VISIBLE
                        binding.txtErrorUp.visibility = View.VISIBLE
                        binding.txtErrorDown.visibility = View.VISIBLE
                    } else Toast.makeText(
                        requireContext(),
                        response.errorMessage,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.progressBar.isVisible = false
                }

                is StatusResponse.Success -> {
                    binding.progressBar.isVisible = false

                    Glide
                        .with(binding.imgMoviePoster.context)
                        .load(response.data.posterUrl)
                        .into(binding.imgMoviePoster)

                    if (response.data.nameRu == null) binding.tvMovieTitle.text =
                        response.data.nameEn
                    else binding.tvMovieTitle.text = response.data.nameRu
                    if (response.data.description != null) binding.tvMovieDescription.text =
                        response.data.description
                    binding.tvMovieCountries.text =
                        response.data.countries.joinToString(", ") { country ->
                            country.country
                        }
                    binding.tvMovieGenres.text = response.data.genres.joinToString(", ") { genre ->
                        genre.genre
                    }
                    binding.tvMovieYear.text = response.data.year

                    binding.btnKp.setOnClickListener {
                        val options = arrayOf("Открыть в браузере", "Поделиться ссылкой")
                        val builder = AlertDialog.Builder(requireActivity())

                        builder.setTitle("Выберите")
                        builder.setItems(options) { _, it ->
                            when (it) {

                                0 -> {
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data = Uri.parse(response.data.webUrl)
                                    startActivity(intent)
                                }

                                1 -> {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, response.data.webUrl)
                                        type = "text/plain"
                                    }
                                    startActivity(Intent.createChooser(sendIntent,"Поделиться с помощью")
                                    )
                                }
                            }
                        }
                        builder.show()
                    }
                }

            }
        }
    }
}