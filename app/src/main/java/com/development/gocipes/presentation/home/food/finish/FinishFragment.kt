package com.development.gocipes.presentation.home.food.finish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.development.gocipes.R
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.utils.Extensions.showImage
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentFinishBinding
import com.development.gocipes.presentation.home.food.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinishFragment : Fragment() {

    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding
    private val navArgs by navArgs<FinishFragmentArgs>()
    private val viewModel by viewModels<FoodViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFinishBinding.inflate(layoutInflater, container, false)
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

    private fun setupView(food: FoodItem) {
        binding?.apply {
            contentFinish.apply {
                sivFood.showImage(requireActivity(), food.gambar ?: "")
                tvName.text = "Selamat anda telah memasak ${food.namaResep}"
            }
            btnTimer.text = "${food.id} menit"
            btnHome.setOnClickListener {
                val option = NavOptions.Builder()
                    .setPopUpTo(R.id.finishFragment, inclusive = true)
                    .build()
                findNavController().navigate(
                    R.id.action_finishFragment_to_homeFragment2,
                    null,
                    option
                )
            }
        }
    }

    private fun onLoading() {
        binding?.apply {
            contentFinish.root.visibility = View.GONE
            tvTime.visibility = View.GONE
            btnTimer.visibility = View.GONE
            btnHome.visibility = View.GONE
        }
    }

    private fun onResult() {
        binding?.apply {
            contentFinish.root.visibility = View.VISIBLE
            tvTime.visibility = View.VISIBLE
            btnTimer.visibility = View.VISIBLE
            btnHome.visibility = View.VISIBLE
            shimmer.apply {
                stopShimmer()
                visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}