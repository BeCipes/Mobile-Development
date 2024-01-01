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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import com.development.gocipes.core.utils.Extensions.showImage
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentDetailAnalysisBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailAnalysisFragment : Fragment() {
    private var _binding: FragmentDetailAnalysisBinding? = null
    private val binding get() = _binding
    private val navArgs by navArgs<DetailAnalysisFragmentArgs>()
    private val viewModel by viewModels<DetailAnalysisViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailAnalysisBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val analysisArgs = navArgs.id
        getIngridientByIdObserver(analysisArgs)
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

    private fun getIngridientByIdObserver(id: Int) {
        viewModel.getIngridientById(id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                }

                is Result.Success -> {
                    setupView(result.data)
                }
            }
        }
    }

    private fun setupView(analysis: IngridientItem) {
        binding?.apply {
            ivIngridient.showImage(requireActivity(), analysis.gambar ?: "")
            tvNameIngridient.text = analysis.namaBahan
            tvDescription.text = analysis.deskripsi
            tvEnergyTotal.text = getString(
                com.development.gocipes.core.R.string.kilo_kalori,
                analysis.gizi?.lemak ?: 0
            )
            tvProteinTotal.text =
                getString(com.development.gocipes.core.R.string.gram,
                    analysis.gizi?.protein ?: 0)
            tvLemakTotal.text =
                getString(com.development.gocipes.core.R.string.gram,
                    analysis.gizi?.lemak ?: 0
                )
            tvKarboTotal.text =
                getString(com.development.gocipes.core.R.string.gram,
                    analysis.gizi?.karbohidrat ?: 0
                )
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