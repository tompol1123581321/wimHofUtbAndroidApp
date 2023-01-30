package com.example.utb_project_wim.screens.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.utb_project_wim.R
import com.example.utb_project_wim.databinding.FragmentThirdBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    private lateinit var viewModel: PracticeViewModel
    private lateinit var binding: FragmentThirdBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_third,
            container,
            false
        )
        viewModel = ViewModelProvider(this)[PracticeViewModel::class.java]
        binding.practiceViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.eventPracticeFinish.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished) practiceFinished()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments

        val breaths = args?.getInt("breaths")
        val rounds = args?.getInt("rounds")
        val secToHold = args?.getInt("secsToHold")


        if (secToHold != null && rounds != null&& breaths != null ) {
                    viewModel.onSetUpPracticeViewModel(breaths,rounds,secToHold)
                }


        binding.homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
        viewModel.onStart()
    }

    private fun practiceFinished() {
        findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("left")
        viewModel.onPracticeFinished()
    }
}

