package com.development.gocipes.presentation.home.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.gocipes.core.data.local.dummy.DummyFood
import com.development.gocipes.core.domain.model.food.Food
import com.development.gocipes.databinding.FragmentSearchBinding
import com.development.gocipes.core.presentation.adapter.SearchAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupShimmer()
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

    private fun setupRecyclerView() {
        val dummyAllFood = DummyFood.dummyAllFood
        searchAdapter = SearchAdapter{ food ->
            navigateToDetailFood(food)
        }

        binding?.rvSearch?.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        searchAdapter.modifyList(dummyAllFood)
        setupSearch()
    }

    private fun setupSearch() {
        binding?.searchView?.apply {
            clearFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    (binding?.rvSearch?.adapter as SearchAdapter).filter(newText)
                    return true
                }
            })
        }
    }

    private fun navigateToDetailFood(food: Food) {
//        val action = SearchFragmentDirections.actionSearchFragmentToDetailFoodFragment(food)
//        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}