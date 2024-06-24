package com.example.moviebox.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebox.data.models.MovieModel
import com.example.moviebox.databinding.ItemMovieBinding

class MoviesAdapter(
    private val openMovieDescription: (movieId: String) -> Unit = { movieId ->
        Log.d("MovieAdapter:", "Open $movieId")
    },
    private val addMovieToFavorites: (movie: MovieModel) -> Boolean = { movie ->
        Log.d("MovieAdapter:", "The movie with id = ${movie.filmId} has been added to favorites")
        true
    },
    private val removeMovieFromFavorites: ((movie: MovieModel) -> Unit)? = null
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var moviesList = mutableListOf<MovieModel>()

    fun updateMovies(movies: List<MovieModel>) {
        val diffCallback = object : DiffUtil.Callback() {

            override fun getOldListSize(): Int = moviesList.size
            override fun getNewListSize(): Int = movies.size

            override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
                return moviesList[oldPosition].filmId == movies[newPosition].filmId
            }

            override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
                return (moviesList[oldPosition].filmId == movies[newPosition].filmId
                        && moviesList[oldPosition].posterUrl == movies[newPosition].posterUrl
                        && moviesList[oldPosition].isFavorite == movies[newPosition].isFavorite)
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        moviesList = movies.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    abstract inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(
            movie: MovieModel,
            openMovieDescription: (movieId: String) -> Unit
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position], openMovieDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return PopularMovieViewHolder(
            binding,
            openMovieDescription,
            addMovieToFavorites,
            removeMovieFromFavorites
        )
    }

    inner class PopularMovieViewHolder(
        private val binding: ItemMovieBinding,
        private val open: (movieId: String) -> Unit,
        private val addMovieToFavorites: (movie: MovieModel) -> Boolean,
        private val removeMovieFromFavorites: ((movie: MovieModel) -> Unit)? = null,
    ) : MovieViewHolder(binding.root) {

        override fun bind(
            movie: MovieModel,
            openMovieDescription: (movieId: String) -> Unit,
        ) {
            with(binding) {
                if (movie.nameRu == null) tvMovieTitle.text = movie.nameEn
                else tvMovieTitle.text = movie.nameRu

                if (movie.isFavorite) {
                    imgFavorite.visibility = View.VISIBLE
                } else {
                    imgFavorite.visibility = View.GONE
                }

                tvMovieDescription.text =
                    "${movie.genres.joinToString(", ") { it.genre }} (${movie.year})"

                Glide
                    .with(imgMovie.context)
                    .load(movie.posterUrlPreview)
                    .centerCrop()
                    .into(imgMovie)

                root.setOnClickListener {
                    open(movie.filmId.toString())
                }

                root.setOnLongClickListener {
                    if (removeMovieFromFavorites != null) {
                        removeMovieFromFavorites?.let { it1 -> it1(movie) }
                    } else addMovieToFavorites(movie)
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}