package com.development.gocipes.presentation.home.article

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.development.gocipes.core.domain.model.article.Article
import com.development.gocipes.core.domain.model.information.Information
import com.development.gocipes.core.presentation.adapter.ArticleGridAdapter
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding
    private lateinit var articleGridAdapter: ArticleGridAdapter
    private val viewModel by viewModels<ArticleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        articleObserver()
        setupShimmer()
    }

    private fun setupShimmer() {
        binding?.apply {
            rvArticle.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                rvArticle.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE

                shimmer.apply {
                    stopShimmer()
                    visibility = View.INVISIBLE
                }
            }, 1500)
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = "Artikel"
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

    private fun articleObserver() {
        viewModel.getAllArticle().observe(viewLifecycleOwner) { result ->
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

    private fun setupView(article: List<Article>) {
        articleGridAdapter = ArticleGridAdapter {}

        val gridCount =
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

        binding?.apply {
            rvArticle.apply {
                adapter = articleGridAdapter
                layoutManager = GridLayoutManager(requireActivity(), gridCount)
            }
        }

        articleGridAdapter.submitList(article)
    }

    private fun onLoading() {
        binding?.apply {
            rvArticle.visibility = View.GONE
        }
    }

    private fun onResult() {
        binding?.apply {
            rvArticle.visibility = View.VISIBLE
            shimmer.apply {
                stopShimmer()
                visibility = View.GONE
            }
        }
    }

    private fun navigateToArticleGraph(information: Information) {
        val action = ArticleFragmentDirections.actionArticleFragmentToArticleGraph(information)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}