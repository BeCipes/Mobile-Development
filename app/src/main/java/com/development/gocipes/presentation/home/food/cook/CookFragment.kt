package com.development.gocipes.presentation.home.food.cook

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
import com.development.gocipes.core.data.remote.response.step.StepItem
import com.development.gocipes.core.presentation.adapter.CookAdapter
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentCookBinding
import com.development.gocipes.presentation.home.food.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CookFragment : Fragment() {

    private var _binding: FragmentCookBinding? = null
    private val binding get() = _binding
    private val navArgs by navArgs<CookFragmentArgs>()
    private val viewModel by viewModels<FoodViewModel>()
    private lateinit var cookAdapter: CookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCookBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stepObserver()
    }

    private fun stepObserver() {
        viewModel.getStep(navArgs.id).observe(viewLifecycleOwner) { result ->
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

    private fun setupView(step: List<StepItem>) {

        setupToolbar()
        setupRecyclerView(step)
        binding?.btnCook?.setOnClickListener { navigateToTimer(navArgs.id) }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = "Langkah-langkah"
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

    private fun setupRecyclerView(listCook: List<StepItem>) {
        cookAdapter = CookAdapter()

        binding?.rvCook?.apply {
            adapter = cookAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        cookAdapter.submitList(listCook)
    }

    private fun navigateToTimer(id: Int) {
        val action =
            CookFragmentDirections.actionCookFragmentToTimerFragment(
                id
            )
        findNavController().navigate(action)
    }

    private fun onLoading() {
        binding?.apply {
            rvCook.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
            btnCook.visibility = View.INVISIBLE
        }
    }

    private fun onResult() {
        binding?.apply {
            rvCook.visibility = View.VISIBLE
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