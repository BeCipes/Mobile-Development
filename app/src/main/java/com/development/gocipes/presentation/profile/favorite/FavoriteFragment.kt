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
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.gocipes.core.data.local.dummy.DummyFavorite
import com.development.gocipes.core.data.remote.response.favorite.GetFavoriteItem
import com.development.gocipes.core.domain.model.favorite.Favorite
import com.development.gocipes.databinding.FragmentFavoriteBinding
import com.development.gocipes.core.presentation.adapter.FavoriteAdapter
import com.development.gocipes.core.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavoriteObserver()
        setupToolbar()
    }

    private fun getFavoriteObserver() {
        viewModel.getFavoriteUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
//                    onResult()
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
//                    onLoading()
                }

                is Result.Success -> {
//                    onResult()
                    setupRecyclerFavorite(result.data)
                }
            }
        }
    }
    


    private fun setupRecyclerFavorite(favorite: List<GetFavoriteItem>) {
        favoriteAdapter = FavoriteAdapter {

        }

        binding?.rvFavorite?.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        favoriteAdapter.submitList(favorite)
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = "Favorite"
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}