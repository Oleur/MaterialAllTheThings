package com.innovorder.android.material.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialContainerTransform
import com.innovorder.android.material.R
import com.innovorder.android.material.databinding.FragmentMovieBinding
import com.innovorder.android.material.domain.entity.FilmEntity
import com.innovorder.android.material.ui.Empty
import com.innovorder.android.material.ui.Error
import com.innovorder.android.material.ui.Success
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

    private val filmId by lazy { requireArguments().getInt("filmId", 1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
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
        binding.title.text = movie.title
        binding.openingCrawl.text = movie.openingCrawl

        binding.poster.setImageResource(getPoster(movie.episodeId))
        binding.poster.animate().scaleX(1f).scaleY(1f).alpha(1f)
                .setDuration(300).setInterpolator(DecelerateInterpolator()).start()
        binding.backdrop.setImageResource(getBackdrop(movie.episodeId))
    }

    private fun applyErrorState(exception: Throwable) {
        Log.e("MovieFragment", exception.message, exception)
    }

    private fun getPoster(filmId: Int): Int {
        return when(filmId) {
            1 -> R.drawable.starwars_poster_1
            2 -> R.drawable.starwars_poster_2
            3 -> R.drawable.starwars_poster_3
            4 -> R.drawable.starwars_poster_4
            5 -> R.drawable.starwars_poster_5
            else -> R.drawable.starwars_poster_6
        }
    }

    private fun getBackdrop(filmId: Int): Int {
        return when(filmId) {
            1 -> R.drawable.starwars_backdrop_1
            2 -> R.drawable.starwars_backdrop_2
            3 -> R.drawable.starwars_backdrop_3
            4 -> R.drawable.starwars_backdrop_4
            5 -> R.drawable.starwars_backdrop_5
            else -> R.drawable.starwars_backdrop_6
        }
    }
}