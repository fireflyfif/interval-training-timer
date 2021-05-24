package com.example.intervaltrainingtimer.ui

import android.os.*
import android.view.*
import androidx.fragment.app.*
import androidx.navigation.fragment.*
import com.example.intervaltrainingtimer.R
import com.example.intervaltrainingtimer.databinding.*
import com.example.intervaltrainingtimer.ui.HomeWorkoutViewModel.Companion.SECONDS_05
import com.example.intervaltrainingtimer.utils.TimeUtils.toDecimalString
import com.example.intervaltrainingtimer.utils.TimeUtils.toMin
import com.example.intervaltrainingtimer.utils.TimeUtils.toSec
import timber.log.*

class WorkoutBeginFragment : Fragment() {

    private var setsExtra: Int = 1
    private var workTimeExtra: Long = 0L
    private var restTimeExtra: Long = 0L
    private var binding: FragmentWorkoutBeginBinding? = null
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Timber.e("WorkoutBeginFragment did not receive bundle information")
            return
        }

        val args: WorkoutBeginFragmentArgs by navArgs()
        setsExtra = args.sets
        workTimeExtra = args.workoutTime
        restTimeExtra = args.restTime
        Timber.d("temp, setsExtra: $setsExtra, workTimeExtra: $workTimeExtra, restTimeExtra: $restTimeExtra")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBeginBinding.inflate(layoutInflater, container, false)
        setCountdownTimerForWorkout(workTimeExtra, restTimeExtra)

        return binding?.root
    }

    private fun setCountdownTimerForWorkout(workMillisInFuture: Long, restMillisInFuture: Long, currentSet: Int = 1) {
        binding?.currentSetText?.text = String.format(getString(R.string.workout_type_set), currentSet)
        binding?.timerTypeText?.text = getString(R.string.workout_type_work)

        timer = object : CountDownTimer(workMillisInFuture, SECONDS_05) {
            override fun onTick(millisUntilFinished: Long) {
                val minUntilFinished = millisUntilFinished.toMin().toDecimalString()
                val secUntilFinished = millisUntilFinished.toSec().toDecimalString()
                binding?.timerText?.text = String.format(getString(R.string.workout_time), minUntilFinished, secUntilFinished)
            }

            override fun onFinish() {
                if (currentSet < setsExtra) {
                    setCountdownTimerForRest(restMillisInFuture, workMillisInFuture, currentSet)
                } else {
                    binding?.timerTypeText?.text = getString(R.string.workout_type_finished)
                }
            }
        }.start()
    }

    private fun setCountdownTimerForRest(restMillisInFuture: Long, workMillisInFuture: Long, currentSet: Int) {
        val timer = object : CountDownTimer(restMillisInFuture, SECONDS_05) {
            override fun onTick(millisUntilFinished: Long) {
                val minUntilFinished = millisUntilFinished.toMin().toDecimalString()
                val secUntilFinished = millisUntilFinished.toSec().toDecimalString()
                binding?.timerText?.text = String.format(getString(R.string.workout_time), minUntilFinished, secUntilFinished)
            }

            override fun onFinish() {
                binding?.timerTypeText?.text = getString(R.string.workout_type_prepare)
                setCountdownTimerForWorkout(workMillisInFuture, restMillisInFuture, currentSet + 1)
            }
        }
        timer.start()
        binding?.timerTypeText?.text = getString(R.string.workout_type_rest)
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }
}