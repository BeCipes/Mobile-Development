package com.development.gocipes.presentation.home.food.timer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.development.gocipes.core.R
import com.development.gocipes.core.data.remote.response.step.StepItem
import com.development.gocipes.core.presentation.adapter.TimerAdapter
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentTimerBinding
import com.development.gocipes.presentation.home.food.FoodViewModel
import com.orbitalsonic.sonictimer.SonicCountDownTimer
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import kotlin.math.roundToInt

@AndroidEntryPoint
class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding
    private lateinit var countDownTimer: SonicCountDownTimer
    private lateinit var timerAdapter: TimerAdapter
    private val viewModel by viewModels<FoodViewModel>()
    private val navArgs by navArgs<TimerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTimerBinding.inflate(layoutInflater, container, false)
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

    private fun setupView(listStep: List<StepItem>) {

        binding?.apply {
            btnNext.setOnClickListener { next() }
            btnPrevious.setOnClickListener { previous() }
        }

        setupTimer(listStep[0].waktu ?: 0, listStep[0].id ?: 0)
        setupToolbar(listStep[0].stepNo ?: 0)
        setupViewPager(listStep)
        pageChange(listStep)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViewPager(steps: List<StepItem>) {
        timerAdapter = TimerAdapter(requireActivity(), steps)

        binding?.viewPager?.apply {
            adapter = timerAdapter
        }

        binding?.viewPager?.setOnTouchListener { _, _ -> true }
    }

    private fun setupToolbar(id: Int) {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = "Step $id"
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

    private fun pageChange(listStep: List<StepItem>) {
        binding?.viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                setupTimer(listStep[position].waktu ?: 0, listStep[position].idResep ?: 0)
                setupToolbar(listStep[position].stepNo ?: 0)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupTimer(timer: Int, idRecipe: Int) {
        val clockTime = (timer * 1000).toLong()
        val progressTime = (clockTime / 1000).toFloat()
        val second = (clockTime / 1000.0f).roundToInt()

        var secondLeft = 0
        countDownTimer = object : SonicCountDownTimer(clockTime, 1000) {
            override fun onTimerTick(timeRemaining: Long) {
                val seconds = (timeRemaining / 1000.0f).roundToInt()
                if (seconds != secondLeft) {
                    secondLeft = seconds
                    binding?.apply {
                        timerFormat(secondLeft, tvTimer)
                    }
                }
            }

            override fun onTimerFinish() {
                binding?.apply {
                    binding?.apply {
                        if (viewPager.currentItem == viewPager.adapter?.count?.minus(1)) {
                            val action =
                                TimerFragmentDirections.actionTimerFragmentToFinishFragment(idRecipe)
                            findNavController().navigate(action)
                        } else {
                            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
                            countDownTimer.startCountDownTimer()
                        }
                    }
                }
            }
        }

        binding?.apply {
            btnPause.setOnClickListener {
                if (countDownTimer.isTimerRunning()) {
                    if (countDownTimer.isTimerPaused()) {
                        countDownTimer.resumeCountDownTimer()
                        btnPause.icon =
                            ContextCompat.getDrawable(
                                requireActivity(),
                                R.drawable.ic_pause
                            )
                    } else {
                        countDownTimer.pauseCountDownTimer()
                        btnPause.icon =
                            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_play)
                    }
                } else {
                    countDownTimer.startCountDownTimer()
                    btnPause.icon =
                        ContextCompat.getDrawable(requireActivity(), R.drawable.ic_pause)
                }
            }
            btnNext.setOnClickListener {
                countDownTimer.cancelCountDownTimer()
                btnPause.icon =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.ic_play)
                next()
            }
            btnPrevious.setOnClickListener {
                countDownTimer.cancelCountDownTimer()
                btnPause.icon =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.ic_play)
                previous()
            }
            timerFormat(second, tvTimer)
            progressCircular.max = progressTime.toInt()
            progressCircular.progress = progressTime.toInt()
        }
    }

    private fun timerFormat(secondsLeft: Int, timeTxt: TextView) {
        binding?.progressCircular?.progress = secondsLeft
        val decimalFormat = DecimalFormat("00")
        val min = (secondsLeft % 3600) / 60
        val seconds = secondsLeft % 60

        val timeFormat = decimalFormat.format(min) + ":" + decimalFormat.format(seconds)

        timeTxt.text = timeFormat
    }

    private fun previous() {
        binding?.apply {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        }
    }

    private fun next() {
        binding?.apply {
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        }
    }

    private fun onLoading() {
        binding?.apply {
            viewPager.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
            progressCircular.visibility = View.INVISIBLE
            btnPrevious.visibility = View.INVISIBLE
            btnPause.visibility = View.INVISIBLE
            btnNext.visibility = View.INVISIBLE
            tvTimer.visibility = View.INVISIBLE
        }
    }

    private fun onResult() {
        binding?.apply {
            viewPager.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
            progressCircular.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE
            btnPause.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
            tvTimer.visibility = View.VISIBLE

            shimmer.apply {
                stopShimmer()
                visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        countDownTimer.cancelCountDownTimer()
    }
}