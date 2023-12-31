package com.development.gocipes.presentation.analysis

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
import com.development.gocipes.core.domain.model.food.Analysis
import com.development.gocipes.core.utils.Extensions.showImage
import com.development.gocipes.databinding.FragmentDetailAnalysisBinding


class DetailAnalysisFragment : Fragment() {
    private var _binding: FragmentDetailAnalysisBinding? = null
    private val binding get() = _binding
    private val navArgs by navArgs<DetailAnalysisFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailAnalysisBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val analysisArgs = navArgs.analysis
        setupView(analysisArgs)
        setupToolbar()
        setupShimmer()
    }

    private fun setupShimmer() {
        binding?.apply {
            toolbar.visibility = View.INVISIBLE
            tvDescription.visibility = View.INVISIBLE
            tvEnergyAnalysis.visibility = View.INVISIBLE
            tvEnergyTotal.visibility = View.INVISIBLE
            tvKarboAnalysis.visibility = View.INVISIBLE
            tvKarboTotal.visibility = View.INVISIBLE
            tvAnalisisGizi.visibility = View.INVISIBLE
            tvLemakAnalysis.visibility = View.INVISIBLE
            tvLemakTotal.visibility = View.INVISIBLE
            tvProteinAnalysis.visibility = View.INVISIBLE
            tvProteinTotal.visibility = View.INVISIBLE
            tvNameIngridient.visibility = View.INVISIBLE
            tvWeightIngridient.visibility = View.INVISIBLE
            ivIngridient.visibility = View.INVISIBLE
            cardView.visibility = View.INVISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                toolbar.visibility = View.VISIBLE
                tvDescription.visibility = View.VISIBLE
                tvEnergyAnalysis.visibility = View.VISIBLE
                tvEnergyTotal.visibility = View.VISIBLE
                tvKarboAnalysis.visibility = View.VISIBLE
                tvKarboTotal.visibility = View.VISIBLE
                tvAnalisisGizi.visibility = View.VISIBLE
                tvLemakAnalysis.visibility = View.VISIBLE
                tvLemakTotal.visibility = View.VISIBLE
                tvProteinAnalysis.visibility = View.VISIBLE
                tvProteinTotal.visibility = View.VISIBLE
                tvNameIngridient.visibility = View.VISIBLE
                tvWeightIngridient.visibility = View.VISIBLE
                ivIngridient.visibility = View.VISIBLE
                cardView.visibility = View.VISIBLE

                shimmer.apply {
                    stopShimmer()
                    visibility = View.INVISIBLE
                }
            }, 1500)
        }
    }

    private fun setupView(analysis: Analysis) {
        binding?.apply {
            ivIngridient.showImage(requireActivity(), analysis.imageUrl)
            tvNameIngridient.text = analysis.name
            tvDescription.text = analysis.description
            tvEnergyTotal.text = getString(
                com.development.gocipes.core.R.string.kilo_kalori,
                analysis.energi.toString()
            )
            tvProteinTotal.text =
                getString(com.development.gocipes.core.R.string.gram, analysis.protein.toString())
            tvLemakTotal.text =
                getString(com.development.gocipes.core.R.string.gram, analysis.lemak.toString())
            tvKarboTotal.text =
                getString(com.development.gocipes.core.R.string.gram, analysis.karbo.toString())
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = getString(com.development.gocipes.core.R.string.bahan)
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