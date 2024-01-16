package com.development.gocipes.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.gocipes.core.data.remote.response.auth.UserResult
import com.development.gocipes.core.data.remote.response.category.Category
import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.domain.model.article.Article
import com.development.gocipes.core.domain.model.technique.Technique
import com.development.gocipes.core.presentation.adapter.ArticleAdapter
import com.development.gocipes.core.presentation.adapter.CategoryAdapter
import com.development.gocipes.core.presentation.adapter.RecipeAdapter
import com.development.gocipes.core.presentation.adapter.TechniqueAdapter
import com.development.gocipes.core.utils.Extensions.showImage
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentHomeBinding
import com.development.gocipes.presentation.home.article.ArticleViewModel
import com.development.gocipes.presentation.home.food.FoodViewModel
import com.development.gocipes.presentation.home.technique.TechniqueViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var techniqueAdapter: TechniqueAdapter
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var recipeAdapter: RecipeAdapter
    private val viewModel by viewModels<HomeViewModel>()
    private val foodViewModel by viewModels<FoodViewModel>()
    private val techniqueViewModel by viewModels<TechniqueViewModel>()
    private val articleViewModel by viewModels<ArticleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryObserver()
        userInfoObserver()
        articleObserver()
        techniqueObserver()
        foodObserver()
    }

    private fun categoryObserver() {
        foodViewModel.getCategoryFood().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {

                }

                is Result.Success -> {
                    val category = result.data.map { it.kategori }
                    setupRecyclerCategory(category)
                }
            }
        }
    }

    private fun foodObserver() {
        foodViewModel.getCategoryFood().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {}
                is Result.Success -> {
                    setupRecyclerViewFood(result.data)
                }
            }
        }
    }

    private fun articleObserver() {
        articleViewModel.getAllArticle().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {}

                is Result.Success -> {
                    setupRecyclerViewArticle(result.data)
                }
            }
        }
    }

    private fun userInfoObserver() {
        viewModel.getUserInfo().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    onResult()
                    Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
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

    private fun techniqueObserver() {
        techniqueViewModel.getAllTechnique().observe(viewLifecycleOwner) { result ->
            when (result) {

                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {}

                is Result.Success -> {
                    setupRecyclerViewTechnique(result.data)
                }
            }
        }
    }

    private fun setupView(userResult: UserResult) {
        val urlPhoto =
            "https://t4.ftcdn.net/jpg/00/64/67/63/360_F_64676383_LdbmhiNM6Ypzb3FM4PPuFP9rHe7ri8Ju.jpg"

        binding?.contentHome?.apply {
            tvName.text = userResult.firstName
            sivProfile.showImage(requireActivity(), userResult.photo ?: urlPhoto)
            searchBar.setOnClickListener { navigateToSearch() }
            tvAllFood.setOnClickListener { navigateToFood() }
            tvAllArticle.setOnClickListener { navigateToArticle() }
            tvAllTechnique.setOnClickListener { navigateToTechnique() }
        }
    }

    private fun setupRecyclerCategory(categories: List<Category>) {
        categoryAdapter = CategoryAdapter { category ->
            navigateToCategory(category)
        }

        binding?.contentHome?.rvCategory?.apply {
            adapter = categoryAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        categoryAdapter.submitList(categories)
    }

    private fun setupRecyclerViewFood(listFood: List<CategoryItem>) {
        recipeAdapter = RecipeAdapter { id ->
            navigateToFoodGraph(id)
        }

        binding?.contentHome?.rvFood?.apply {
            adapter = recipeAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        recipeAdapter.submitList(listFood)
    }

    private fun setupRecyclerViewArticle(listArticle: List<Article>) {
        articleAdapter = ArticleAdapter { id ->
            navigateToArticleGraph(id)
        }

        binding?.contentHome?.rvGuide?.apply {
            adapter = articleAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        articleAdapter.submitList(listArticle)
    }

    private fun setupRecyclerViewTechnique(listTechnique: List<Technique>) {
        techniqueAdapter = TechniqueAdapter { id ->
            navigateToTechniqueGraph(id)
        }

        binding?.contentHome?.rvTechnique?.apply {
            adapter = techniqueAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        techniqueAdapter.submitList(listTechnique)
    }

    private fun onLoading() {
        binding?.apply {
            contentHome.root.visibility = View.GONE
        }
    }

    private fun onResult() {
        binding?.apply {
            contentHome.root.visibility = View.VISIBLE
            shimmer.apply {
                stopShimmer()
                visibility = View.GONE
            }
        }
    }

    private fun navigateToFood() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToFoodFragment()
        findNavController().navigate(action)
    }

    private fun navigateToFoodGraph(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFoodFragment(id)
        findNavController().navigate(action)
    }

    private fun navigateToArticle() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToArticleFragment()
        findNavController().navigate(action)
    }

    private fun navigateToArticleGraph(id: Int) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToArticleGraph(id)
        findNavController().navigate(action)
    }

    private fun navigateToTechnique() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToTechniqueFragment()
        findNavController().navigate(action)
    }

    private fun navigateToTechniqueGraph(id: Int) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToTechniqueGraph(id)
        findNavController().navigate(action)
    }

    private fun navigateToSearch() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun navigateToCategory(category: Category) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
                category
            )
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}