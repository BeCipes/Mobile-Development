package com.development.gocipes.presentation.home.technique

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
import androidx.recyclerview.widget.GridLayoutManager
import com.development.gocipes.core.domain.model.technique.Technique
import com.development.gocipes.core.presentation.adapter.InformationGridAdapter
import com.development.gocipes.core.presentation.adapter.TechniqueGridAdapter
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentTechniqueBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TechniqueFragment : Fragment() {

    private var _binding: FragmentTechniqueBinding? = null
    private val binding get() = _binding
    private lateinit var informationAdapter: InformationGridAdapter
    private lateinit var techniqueGridAdapter: TechniqueGridAdapter
    private val viewModel by viewModels<TechniqueViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTechniqueBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        techniqueObserver()
    }

    private fun techniqueObserver() {
        viewModel.getAllTechnique().observe(viewLifecycleOwner) { result ->
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
                title = "Teknik Memasak"
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

    private fun setupView(listTechnique: List<Technique>) {
        techniqueGridAdapter = TechniqueGridAdapter { id ->
            navigateToTechniqueGraph(id)
        }
        val gridCount =
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

        binding?.apply {
            rvArticle.apply {
                adapter = techniqueGridAdapter
                layoutManager = GridLayoutManager(requireActivity(), gridCount)
            }
        }

        techniqueGridAdapter.submitList(listTechnique)
    }

    private fun navigateToTechniqueGraph(id: Int) {
        val action =
            TechniqueFragmentDirections.actionTechniqueFragmentToTechniqueGraph(id)
        findNavController().navigate(action)
    }

    private fun onLoading() {
        binding?.apply {
            rvArticle.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
        }
    }

    private fun onResult() {
        binding?.apply {
            rvArticle.visibility = View.VISIBLE
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