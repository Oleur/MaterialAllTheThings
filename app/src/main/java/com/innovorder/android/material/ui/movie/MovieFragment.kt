package com.innovorder.android.material.ui.movie

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
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
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 1000L
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            interpolator = DecelerateInterpolator()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.TRANSPARENT)
        }
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

        val film = fragmentArgs.film
        binding.backdrop.transitionName = film.episodeId.toString()
        binding.title.transitionName = film.title

        binding.title.text = film.title

        Picasso.get()
            .load(getBackdrop(film.episodeId))
            .into(binding.backdrop, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                }
            })

        lifecycleScope.launch {
            with(viewModel) {
                getFilm(getId(film.episodeId))
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

    private fun getId(episodeId: Int) = when(episodeId) {
        1 -> 4
        2 -> 5
        3 -> 6
        4 -> 1
        5 -> 2
        else -> 3
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

            Picasso.get()
                .load(getPoster(movie.episodeId))
                .into(binding.cover, object : Callback {
                    override fun onSuccess() {
                        animateCover()
                    }

                    override fun onError(e: Exception?) {
                        animateCover()
                    }
                })
        }
    }

    private fun animateCover() {
        binding.cover.animate().scaleX(1f).scaleY(1f).alpha(1f)
            .setDuration(300).setInterpolator(DecelerateInterpolator()).setStartDelay(500).start()
        binding.cover.setOnClickListener { findNavController().navigateUp() }
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