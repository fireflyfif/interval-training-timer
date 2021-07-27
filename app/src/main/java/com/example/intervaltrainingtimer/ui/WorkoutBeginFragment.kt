package com.example.intervaltrainingtimer.ui

import android.content.*
import android.os.*
import android.view.*
import androidx.core.content.res.*
import androidx.fragment.app.*
import androidx.navigation.fragment.*
import com.example.intervaltrainingtimer.R
import com.example.intervaltrainingtimer.databinding.*
import com.example.intervaltrainingtimer.ui.HomeWorkoutViewModel.Companion.SECOND_01
import com.example.intervaltrainingtimer.utils.*
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
    private var localContext: Context? = null

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
        localContext?.let {
            binding?.currentSetText?.text = String.format(getString(R.string.workout_type_set), currentSet)
            binding?.timerTypeText?.text = getString(R.string.workout_type_work)
            binding?.layoutRoot?.background = null
        }

        timer = object : CountDownTimer(workMillisInFuture, SECOND_01) {
            override fun onTick(millisUntilFinished: Long) {
                val minUntilFinished = millisUntilFinished.toMin().toDecimalString()
                val secUntilFinished = millisUntilFinished.toSec().toDecimalString()
                localContext?.let {
                    binding?.timerText?.text = String.format(getString(R.string.workout_time), minUntilFinished, secUntilFinished)
                }
            }

            override fun onFinish() {
                if (currentSet < setsExtra) {
                    setCountdownTimerForRest(restMillisInFuture, workMillisInFuture, currentSet)
                } else {
                    localContext?.let {
                        binding?.timerTypeText?.text = getString(R.string.workout_type_finished)
                        binding?.layoutRoot?.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
                    }
                }
            }
        }.start()
    }

    private fun setCountdownTimerForRest(restMillisInFuture: Long, workMillisInFuture: Long, currentSet: Int) {
        timer = object : CountDownTimer(restMillisInFuture, SECOND_01) {
            override fun onTick(millisUntilFinished: Long) {
                val minUntilFinished = millisUntilFinished.toMin().toDecimalString()
                val secUntilFinished = millisUntilFinished.toSec().toDecimalString()
                localContext?.let {
                    binding?.timerText?.text = String.format(getString(R.string.workout_time), minUntilFinished, secUntilFinished)
                }
                if (millisUntilFinished <= 3000L) {
                    BeepHelper.beep(500)
                }
            }

            override fun onFinish() {
                localContext?.let {
                    binding?.timerTypeText?.text = getString(R.string.workout_type_prepare)
                }
                setCountdownTimerForWorkout(workMillisInFuture, restMillisInFuture, currentSet + 1)
                BeepHelper.releaseToneGenerator()
            }
        }.start()
        localContext?.let {
            binding?.timerTypeText?.text = getString(R.string.workout_type_rest)
            binding?.layoutRoot?.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.orange, null))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localContext = context
    }

    override fun onDetach() {
        super.onDetach()
        localContext = null
    }

    override fun onPause() {
        super.onPause()
        //        timer?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer = null
    }
}