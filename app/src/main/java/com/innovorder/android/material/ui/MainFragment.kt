package com.innovorder.android.material.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.innovorder.android.material.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainAdapter: MainAdapter

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


        lifecycleScope.launchWhenResumed {
            viewModel.getFilms()

            viewModel.state.collect { state ->
                when (state) {
                    Empty -> applyEmptyState()
                    is Success -> applySuccessState(state.data)
                    is Error -> applyErrorState(state.exception)
                }
            }
        }
        initList()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList() {
        mainAdapter = MainAdapter()
        with(binding.list) {
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = mainAdapter
        }
    }

    private fun applyEmptyState() {
        Log.w("MainFragment", "Null value")
    }

    private fun applySuccessState(mainModel: MainModel) {
        mainAdapter.submitList(mainModel.films)
    }

    private fun applyErrorState(exception: Throwable) {
        Log.e("MainFragment", exception.message, exception)
    }
    
}
