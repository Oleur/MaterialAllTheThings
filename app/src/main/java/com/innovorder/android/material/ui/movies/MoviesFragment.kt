package com.innovorder.android.material.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.innovorder.android.material.R
import com.innovorder.android.material.core.transition.SpringAddItemAnimator
import com.innovorder.android.material.databinding.FragmentMainBinding
import com.innovorder.android.material.ui.Empty
import com.innovorder.android.material.ui.Error
import com.innovorder.android.material.ui.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MoviesFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reenterTransition = MaterialElevationScale(true)
        exitTransition = MaterialElevationScale(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        initList()

        lifecycleScope.launch {
            with(viewModel) {
                getFilms()
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

    private fun initList() {
        moviesAdapter = MoviesAdapter { list, filmId ->
            navigateToMovie(list, filmId)
        }
        with(binding.list) {
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = moviesAdapter
            itemAnimator = SpringAddItemAnimator()
        }
    }

    private fun applyEmptyState() {
        Log.w("MainFragment", "Null value")
    }

    private fun applySuccessState(moviesModel: MoviesModel) {
        moviesAdapter.submitList(moviesModel.films)
    }

    private fun applyErrorState(exception: Throwable) {
        Log.e("MainFragment", exception.message, exception)
    }

    private fun navigateToMovie(list: List<Pair<View, String>>, filmId: Int) {
        val extras = FragmentNavigatorExtras(*list.toTypedArray())
        findNavController().navigate(R.id.action_mainFragment_to_movieFragment, bundleOf("filmId" to filmId), null, extras)
    }

}
