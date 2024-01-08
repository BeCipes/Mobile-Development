package com.development.gocipes.presentation.profile.favorite

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.presentation.adapter.IngredientAdapter
import com.development.gocipes.core.utils.Extensions.showImage
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentDetailFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFavoriteFragment : Fragment() {

    private var _binding: FragmentDetailFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var ingredientAdapter: IngredientAdapter
    private val viewModel by viewModels<FavoriteViewModel>()
    private val navArgs by navArgs<DetailFavoriteFragmentArgs>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodObserver()
    }

    private fun foodObserver() {
        viewModel.getRecipeById(navArgs.id).observe(viewLifecycleOwner) { result ->
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

    private fun setupView(food: FoodItem?) {
        val fat = food?.informasiGizi?.lemak ?: 0
        val carbohydrate = food?.informasiGizi?.karbohidrat ?: 0
        val protein = food?.informasiGizi?.protein ?: 0

        binding?.contentDetail?.apply {
            sivFood.showImage(requireActivity(), food?.gambar ?: "")
            tvDescription.text = food?.deskripsi
//            tvMinutes.text = "${food?.id} menit"
            contentCalories.apply {
                pbFat.progress = fat
                tvFat.text = "$fat%"
                pbCarbohydrates.progress = carbohydrate
                tvCarbohydrates.text = "$carbohydrate%"
                pbProtein.progress = protein
                tvProtein.text = "$protein%"
            }
        }
        setupRecyclerIngredient(food?.bahan ?: emptyList())
        setupToolbar(food)
    }

    private fun setupRecyclerIngredient(listIngredient: List<String>) {
        ingredientAdapter = IngredientAdapter()

        binding?.contentDetail?.rvIngredients?.apply {
            adapter = ingredientAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        ingredientAdapter.submitList(listIngredient)
    }

    private fun setupToolbar(food: FoodItem?) {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = food?.namaResep
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

    private fun onLoading() {
        binding?.apply {
            contentDetail.root.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
        }
    }

    private fun onResult() {
        binding?.apply {
            contentDetail.root.visibility = View.VISIBLE
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