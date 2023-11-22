package com.development.gocipes.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.gocipes.data.DummyCategory
import com.development.gocipes.data.DummyFood
import com.development.gocipes.data.DummyInformation
import com.development.gocipes.databinding.FragmentHomeBinding
import com.development.gocipes.model.Category
import com.development.gocipes.model.Food
import com.development.gocipes.model.Information
import com.development.gocipes.utils.Extensions.showImage
import com.development.gocipes.view.main.home.adapter.CategoryAdapter
import com.development.gocipes.view.main.home.adapter.FoodAdapter
import com.development.gocipes.view.main.home.adapter.InformationAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var informationAdapter: InformationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listCategory = DummyCategory.dummyCategory
        val listFood = DummyFood.dummyFood
        val listGuide = DummyInformation.dummyArticle
        val listTechnique = DummyInformation.dummyTechnique

        val name = "Mutiara!"
        val url =
            "https://www.betterup.com/hubfs/Blog%20Images/authentic-self-person-smiling-at-camera.jpg"

        binding?.contentHome?.apply {
            sivProfile.showImage(requireActivity(), url)
            tvName.text = name
            tvAllFood.setOnClickListener { navigateToFood() }
            tvAllArticle.setOnClickListener { navigateToArticle() }
            tvAllTechnique.setOnClickListener { navigateToTechnique() }
        }

        setupRecyclerCategory(listCategory)
        setupRecyclerViewFood(listFood)
        setupRecyclerViewGuide(listGuide)
        setupRecyclerViewTechnique(listTechnique)
    }

    private fun setupRecyclerCategory(category: List<Category>) {
        categoryAdapter = CategoryAdapter()

        binding?.contentHome?.rvCategory?.apply {
            adapter = categoryAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        categoryAdapter.submitList(category)
    }

    private fun setupRecyclerViewFood(listFood: List<Food>) {
        foodAdapter = FoodAdapter { food ->
            navigateToDetailFood(food)
        }

        binding?.contentHome?.rvFood?.apply {
            adapter = foodAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        foodAdapter.submitList(listFood)
    }

    private fun setupRecyclerViewGuide(listInformation: List<Information>) {
        informationAdapter = InformationAdapter { information ->
            navigateDetailArticle(information)
        }

        binding?.contentHome?.rvGuide?.apply {
            adapter = informationAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        informationAdapter.submitList(listInformation)
    }

    private fun setupRecyclerViewTechnique(listTechnique: List<Information>) {
        informationAdapter = InformationAdapter { information ->
            navigateToDetailTechnique(information)
        }

        binding?.contentHome?.rvTechnique?.apply {
            adapter = informationAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        informationAdapter.submitList(listTechnique)
    }

    private fun navigateToDetailFood(food: Food) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailFoodFragment(
                food
            )
        findNavController().navigate(action)
    }

    private fun navigateToFood() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToFoodFragment()
        findNavController().navigate(action)
    }

    private fun navigateToArticle() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToArticleFragment()
        findNavController().navigate(action)
    }

    private fun navigateDetailArticle(information: Information) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailArticleFragment(
                information
            )
        findNavController().navigate(action)
    }

    private fun navigateToTechnique() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToTechniqueFragment()
        findNavController().navigate(action)
    }

    private fun navigateToDetailTechnique(information: Information) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailTechniqueFragment(
                information
            )
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}