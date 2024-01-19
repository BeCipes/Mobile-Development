package com.development.gocipes.presentation.home.food.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.presentation.adapter.IngredientAdapter
import com.development.gocipes.core.utils.Extensions.showImage
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentDetailFoodBinding
import com.development.gocipes.presentation.profile.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.development.gocipes.core.R as Resource

@AndroidEntryPoint
class DetailFoodFragment : Fragment() {

    private var _binding: FragmentDetailFoodBinding? = null
    private val binding get() = _binding
    private val navArgs by navArgs<DetailFoodFragmentArgs>()
    private var menuDetail: Menu? = null
//    private var statusFavorite: Boolean = false
    private lateinit var ingredientAdapter: IngredientAdapter
    private val viewModel by viewModels<DetailFoodViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailFoodBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idArgs = navArgs.id
        getFoodByIdObserver(idArgs)
    }

    private fun getFoodByIdObserver(id: Int) {
        viewModel.getFoodById(id).observe(viewLifecycleOwner) { result ->
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

    private fun setupView(categoryItem: CategoryItem) {
        val food = categoryItem.resep
        val fat = food?.informasiGizi?.lemak ?: 0
        val carbohydrate = food?.informasiGizi?.karbohidrat ?: 0
        val protein = food?.informasiGizi?.protein ?: 0

        binding?.contentDetail?.apply {
            sivFood.showImage(requireActivity(), food?.gambar ?: "")
            tvDescription.text = food?.deskripsi
            tvMinutes.text = "${food?.id} menit"
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
        binding?.btnCook?.setOnClickListener { navigateToCook(food?.id ?: 0) }
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

//        val menuHost: MenuHost = requireActivity()
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(com.development.gocipes.R.menu.menu_detail, menu)
//                menuDetail = menu
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                return when (menuItem.itemId) {
//                    com.development.gocipes.R.id.btn_favorite -> {
//                        statusFavorite = !statusFavorite
//                        setIsFavorite(statusFavorite, food?.id ?: 0)
//                        true
//                    }
//
//                    android.R.id.home -> {
//                        requireActivity().onBackPressedDispatcher.onBackPressed()
//                        true
//                    }
//
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setIsFavorite(favorite: Boolean, id: Int) {
        if (menuDetail == null) return
        val menuItem = menuDetail?.findItem(com.development.gocipes.R.id.btn_favorite)
        if (favorite) {
            menuItem?.icon =
                ContextCompat.getDrawable(requireActivity(), Resource.drawable.ic_favorite)
            favoriteViewModel.addFavorite(id).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> {}
                    is Result.Success -> {
                        favorite
                        Toast.makeText(context, "${result.data.resep?.namaResep} ditambahkan ke Favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            menuItem?.icon =
                ContextCompat.getDrawable(requireActivity(), Resource.drawable.ic_favorite_border)
            favoriteViewModel.deleteFavorite(id).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> {}
                    is Result.Success -> {
                        !favorite
                    }
                }
            }
        }
    }

    private fun navigateToCook(id: Int) {
        val action =
            DetailFoodFragmentDirections.actionDetailFoodFragmentToCookFragment(
                id
            )
        findNavController().navigate(action)
    }

    private fun onLoading() {
        binding?.apply {
            contentDetail.root.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
            btnCook.visibility = View.INVISIBLE
        }
    }

    private fun onResult() {
        binding?.apply {
            contentDetail.root.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
            btnCook.visibility = View.VISIBLE
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