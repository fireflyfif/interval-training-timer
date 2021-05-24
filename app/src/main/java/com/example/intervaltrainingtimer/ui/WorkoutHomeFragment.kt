package com.example.intervaltrainingtimer.ui

import android.os.*
import android.view.*
import androidx.core.widget.*
import androidx.fragment.app.*
import androidx.navigation.*
import com.example.intervaltrainingtimer.databinding.*
import com.example.intervaltrainingtimer.utils.TimeUtils.toDecimalString
import com.example.intervaltrainingtimer.utils.TimeUtils.toMin
import com.example.intervaltrainingtimer.utils.TimeUtils.toSec

class WorkoutHomeFragment : Fragment() {

    private val viewModel: HomeWorkoutViewModel by viewModels()
    private var binding: FragmentWorkoutHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutHomeBinding.inflate(inflater, container, false)
        binding?.apply {
            setupSets()
            setupWorkoutTime()
            setupRestingTime()
            viewModel.combinedData.observe(viewLifecycleOwner, { values ->
                startWorkout.setOnClickListener {
                    it.findNavController().navigate(
                        WorkoutHomeFragmentDirections.actionStartWorkout(
                            values.sets!!,
                            values.workoutTimeInMillis!!,
                            values.restingTimeInMillis!!
                        )
                    )
                }
            })
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
        })
        workMinusButton.setOnClickListener {
            viewModel.decrementWorkTime()
        }
        workPlusButton.setOnClickListener {
            viewModel.incrementWorkTime()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}