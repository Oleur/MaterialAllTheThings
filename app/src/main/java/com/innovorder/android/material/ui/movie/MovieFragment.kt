package com.innovorder.android.material.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.innovorder.android.material.R
import com.innovorder.android.material.databinding.FragmentMovieBinding
import com.innovorder.android.material.domain.entity.FilmEntity
import com.innovorder.android.material.ui.Empty
import com.innovorder.android.material.ui.Error
import com.innovorder.android.material.ui.Success
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    private val fragmentArgs by navArgs<MovieFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filmId = fragmentArgs.filmId
        binding.cover.transitionName = filmId.toString()

        Picasso.get()
            .load(getBackdrop(filmId))
            .into(binding.cover, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                }
            })


        lifecycleScope.launch {
            with(viewModel) {
                getFilm(filmId)
                state.collect { state ->
                    when (state) {
                        Empty -> applyEmptyState()
                        is Success -> applySuccessState(state.data)
                        is Error -> applyErrorState(state.exception)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun applyEmptyState() {
        Log.w("MovieFragment", "Null value")
    }

    private fun applySuccessState(movie: FilmEntity) {
        with(binding) {
            title.text = movie.title
            openingCrawl.text = movie.openingCrawl
            backdrop.setImageResource(getBackdrop(movie.episodeId))


        }
    }

    private fun applyErrorState(exception: Throwable) {
        Log.e("MovieFragment", exception.message, exception)
    }

    private fun getPoster(filmId: Int): Int = when (filmId) {
        1 -> R.drawable.starwars_poster_1
        2 -> R.drawable.starwars_poster_2
        3 -> R.drawable.starwars_poster_3
        4 -> R.drawable.starwars_poster_4
        5 -> R.drawable.starwars_poster_5
        else -> R.drawable.starwars_poster_6
    }

    private fun getBackdrop(filmId: Int): Int = when (filmId) {
        1 -> R.drawable.starwars_backdrop_1
        2 -> R.drawable.starwars_backdrop_2
        3 -> R.drawable.starwars_backdrop_3
        4 -> R.drawable.starwars_backdrop_4
        5 -> R.drawable.starwars_backdrop_5
        else -> R.drawable.starwars_backdrop_6
    }

}