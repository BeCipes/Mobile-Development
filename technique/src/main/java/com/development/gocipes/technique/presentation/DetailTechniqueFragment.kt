package com.development.gocipes.technique.presentation

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
import com.development.gocipes.core.domain.model.technique.Technique
import com.development.gocipes.core.utils.Result
import com.development.gocipes.technique.databinding.FragmentDetailTechniqueBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTechniqueFragment : Fragment() {

    private var _binding: FragmentDetailTechniqueBinding? = null
    private val binding get() = _binding
    private val navArgs by navArgs<DetailTechniqueFragmentArgs>()
    private val viewModel by viewModels<DetailTechniqueViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailTechniqueBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        techniqueObserver()
    }

    private fun techniqueObserver() {
        viewModel.getTechniqueById(navArgs.id).observe(viewLifecycleOwner) { result ->
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

    private fun setupView(technique: Technique) {

        binding?.contentDetailTechnique?.apply {
            tvTitle.text = technique.title
            tvPractice.text = technique.description
        }

        setupToolbar()
        setUpYoutubeApi(technique)
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowTitleEnabled(false)
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

    private fun setUpYoutubeApi(technique: Technique) {
        binding?.contentDetailTechnique?.apply {
            lifecycle.addObserver(ytPlayer)
            ytPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = technique.source
                    if (videoId != null) {
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                }
            })
        }
    }

    private fun onLoading() {
        binding?.apply {
            contentDetailTechnique.root.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
        }
    }

    private fun onResult() {
        binding?.apply {
            contentDetailTechnique.root.visibility = View.VISIBLE
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