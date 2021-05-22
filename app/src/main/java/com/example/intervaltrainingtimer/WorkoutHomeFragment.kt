package com.example.intervaltrainingtimer

import android.os.*
import android.view.*
import androidx.core.widget.*
import androidx.fragment.app.*
import com.example.intervaltrainingtimer.databinding.*
import timber.log.*

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
                Timber.d("temp, edit text, it: $it")
                viewModel.setSetsFromUserInput(it.toString())
            }
        }
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}