package com.example.intervaltrainingtimer

import android.os.*
import android.view.*
import androidx.core.widget.*
import androidx.fragment.app.*
import com.example.intervaltrainingtimer.TimeUtils.toDecimalString
import com.example.intervaltrainingtimer.TimeUtils.toMin
import com.example.intervaltrainingtimer.TimeUtils.toSec
import com.example.intervaltrainingtimer.databinding.*
import timber.log.*

class WorkoutHomeFragment : Fragment() {

    private val viewModel: HomeWorkoutViewModel by viewModels()
    private var binding: FragmentWorkoutHomeBinding? = null
    private lateinit var timer : CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutHomeBinding.inflate(inflater, container, false)
        viewModel.calculateWorkout()
        binding?.apply {
            setupSets()
            setupWorkoutTime()
            setupRestingTime()
            startWorkout.setOnClickListener {
                timer.start()
            }
        }
        return binding?.root
    }

    private fun FragmentWorkoutHomeBinding.setupSets() {
        setsMinusButton.setOnClickListener {
            viewModel.decrementNumberOfSets()
        }
        setsPlusButton.setOnClickListener {
            viewModel.incrementNumberOfSets()
        }
        viewModel.numberOfSets.observe(viewLifecycleOwner, { sets ->
            sets?.let {
                setDigit.setText(it.toString())
            }
        })
        setDigit.addTextChangedListener {
            viewModel.setSetsFromUserInput(it.toString())
        }
    }

    private fun FragmentWorkoutHomeBinding.setupRestingTime() {
        viewModel.restPeriodInMilliseconds.observe(viewLifecycleOwner, { restInMillis ->
            val min = restInMillis.toMin().toDecimalString()
            val sec = restInMillis.toSec().toDecimalString()
            restTimeMin.setText(min)
            restTimeSec.setText(sec)
        })
        restMinusButton.setOnClickListener {
            viewModel.decrementRestingTime()
        }
        restPlusButton.setOnClickListener {
            viewModel.incrementRestingTime()
        }
    }

    private fun FragmentWorkoutHomeBinding.setupWorkoutTime() {
        viewModel.workoutTimeInMillisecond.observe(viewLifecycleOwner, { workInMillis ->
            val min = workInMillis.toMin().toDecimalString()
            val sec = workInMillis.toSec().toDecimalString()
            workTimeMin.setText(min)
            workTimeSec.setText(sec)
            setCountdownTimer(workInMillis)
        })
        workMinusButton.setOnClickListener {
            viewModel.decrementWorkTime()
        }
        workPlusButton.setOnClickListener {
            viewModel.incrementWorkTime()
        }
    }

    private fun setCountdownTimer(millisInFuture: Long) {
        timer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minUntilFinished = millisUntilFinished.toMin().toDecimalString()
                val secUntilFinished = millisUntilFinished.toSec().toDecimalString()
                Timber.d("temp, minUntilFinished: $minUntilFinished : secUntilFinished: $secUntilFinished")
                binding?.timerText?.text = "$minUntilFinished : $secUntilFinished"
            }

            override fun onFinish() {
                Timber.d("temp, Time is out")
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}