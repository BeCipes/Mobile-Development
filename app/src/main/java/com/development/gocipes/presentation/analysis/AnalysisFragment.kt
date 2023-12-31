package com.development.gocipes.presentation.analysis

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import com.development.gocipes.core.presentation.adapter.AnalysisAdapter
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentAnalysisBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding
    private lateinit var adapterAnalysis: AnalysisAdapter
    private val ingridientViewModel by viewModels<AnalysisViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnalysisBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingridientObserver()
        setupShimmer()
    }

    private fun setupShimmer() {
        binding?.apply {
            searchBar.visibility = View.INVISIBLE
            tvAnalysisHead.visibility = View.INVISIBLE
            rvAnalysis.visibility = View.INVISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                searchBar.visibility = View.VISIBLE
                tvAnalysisHead.visibility = View.VISIBLE
                rvAnalysis.visibility = View.VISIBLE

                shimmer.apply {
                    stopShimmer()
                    visibility = View.INVISIBLE
                }
            }, 1500)
        }
    }

    private fun ingridientObserver() {
        ingridientViewModel.getAllIngridient().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {}

                is Result.Success -> {
                    setupRecycler(result.data)
                }
            }
        }
    }

    private fun setupRecycler(listIngridient: List<IngridientItem>) {
        adapterAnalysis = AnalysisAdapter { id ->
            navigateToDetail(id)
        }
        binding?.apply {
            rvAnalysis.apply {
                adapter = adapterAnalysis
                layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
            }
        }
        adapterAnalysis.submitList(listIngridient)
    }

    private fun navigateToDetail(id: Int) {
        val action = AnalysisFragmentDirections.actionAnalysisFragmentToDetailAnalysisFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}