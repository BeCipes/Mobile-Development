package com.development.gocipes.presentation.home.category

import android.content.res.Configuration
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
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.development.gocipes.core.presentation.adapter.FoodGridAdapter
import com.development.gocipes.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding
    private lateinit var foodGridAdapter: FoodGridAdapter
    private val navArgs by navArgs<CategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupView()
        setupShimmer()
    }

    private fun setupShimmer() {
        binding?.apply {
            rvFood.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                rvFood.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE

                shimmer.apply {
                    stopShimmer()
                    visibility = View.INVISIBLE
                }
            }, 1500)
        }
    }

    private fun setupToolbar() {
        val categoryArgs = navArgs.category
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = categoryArgs.name
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

    private fun setupView() {
        val foodList = navArgs.category.listFood
        val gridCount =
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

        foodGridAdapter = FoodGridAdapter { food ->
//            navigateToDetailFood(food)
        }

        binding?.rvFood?.apply {
            layoutManager = GridLayoutManager(requireActivity(), gridCount)
            adapter = foodGridAdapter
            setHasFixedSize(true)
        }

        foodGridAdapter.submitList(foodList)
    }

//    private fun navigateToDetailFood(food: Food) {
//        val action = CategoryFragmentDirections.actionCategoryFragmentToDetailFoodFragment(food)
//        findNavController().navigate(action)
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}