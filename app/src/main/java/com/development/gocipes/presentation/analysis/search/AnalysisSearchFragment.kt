package com.development.gocipes.presentation.analysis.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import com.development.gocipes.core.presentation.adapter.SearchAnalysisAdapter
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentAnalysisSearchBinding
import com.development.gocipes.presentation.analysis.AnalysisViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisSearchFragment : Fragment() {

    private var _binding: FragmentAnalysisSearchBinding ?= null
    private val binding get() = _binding
    private lateinit var searchAdapter: SearchAnalysisAdapter
    private val viewModel by viewModels<AnalysisViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAnalysisSearchBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        searchObserver()
        setupShimmer()
    }

    private fun searchObserver() {
        viewModel.getAllIngridient().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {

                }

                is Result.Success -> {
                    setupRecyclerView(result.data)
                }
            }
        }
    }

    private fun setupRecyclerView(data: List<IngridientItem>) {

        searchAdapter = SearchAnalysisAdapter{ id ->
            navigateToDetail(id)
        }

        binding?.rvSearch?.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        searchAdapter.modifyList(data)
        setupSearch()
    }

    private fun setupShimmer() {
        binding?.apply {
            rvSearch.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
            searchView.visibility = View.INVISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                rvSearch.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE
                searchView.visibility = View.VISIBLE

                shimmer.apply {
                    stopShimmer()
                    visibility = View.INVISIBLE
                }
            }, 1500)
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowTitleEnabled(false)
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                findNavController().navigateUp()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    private fun setupSearch() {
        binding?.searchView?.apply {
            clearFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    (binding?.rvSearch?.adapter as SearchAnalysisAdapter).filter(newText)
                    return true
                }
            })
        }
    }

    private fun navigateToDetail(id: Int) {
        val action = AnalysisSearchFragmentDirections
            .actionAnalysisSearchFragmentToDetailAnalysisFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

