package com.development.gocipes.article.presentation

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
import com.development.gocipes.article.databinding.FragmentDetailArticleBinding
import com.development.gocipes.core.domain.model.information.Information
import com.development.gocipes.core.utils.Extensions.showImage

class DetailArticleFragment : Fragment() {
    private var _binding: FragmentDetailArticleBinding? = null
    private val binding get() = _binding
    private val navArgs by navArgs<DetailArticleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailArticleBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleArgs = navArgs.information

        setupView(articleArgs)
        setupToolbar()
        setupShimmer()
    }

    private fun setupShimmer() {
        binding?.apply {
            contentDetailArticle.root.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                contentDetailArticle.root.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE

                shimmer.apply {
                    stopShimmer()
                    visibility = View.INVISIBLE
                }
            }, 2000)
        }
    }

    private fun setupView(information: Information) {
        binding?.apply {
            contentDetailArticle.apply {
                ivArticlePhoto.showImage(requireActivity(), information.imageUrl)
                tvArticleHeader.text = information.name
                tvArticleContent.text = information.description
                tvAuthor.text = information.author
                tvDateRelease.text = information.release
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}