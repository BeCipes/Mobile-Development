package com.development.gocipes.presentation.home.category

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.presentation.adapter.RecipeGridAdapter
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding
    private lateinit var recipeGridAdapter: RecipeGridAdapter
    private val navArgs by navArgs<CategoryFragmentArgs>()
    private val viewModel by viewModels<CategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryObserver()
        setupToolbar()
    }

    private fun categoryObserver() {
        val id = navArgs.category.id
        viewModel.getRecipesByCategory(id ?: 0).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    onResult()
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    onLoading()
                }

                is Result.Success -> {
                    onResult()
                    setupView(result.data)
                }
            }
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = navArgs.category.namaKategori
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

    private fun setupView(recipes: List<CategoryItem>) {
        val gridCount =
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

        recipeGridAdapter = RecipeGridAdapter { id ->
            navigateToDetailFood(id)
        }

        binding?.rvFood?.apply {
            layoutManager = GridLayoutManager(requireActivity(), gridCount)
            adapter = recipeGridAdapter
            setHasFixedSize(true)
        }

        recipeGridAdapter.submitList(recipes)
    }

    private fun navigateToDetailFood(id: Int) {
        val action = CategoryFragmentDirections.actionCategoryFragmentToDetailFoodFragment(id)
        findNavController().navigate(action)
    }

    private fun onLoading() {
        binding?.apply {
            rvFood.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
        }
    }

    private fun onResult() {
        binding?.apply {
            rvFood.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE

            shimmer.apply {
                stopShimmer()
                visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}