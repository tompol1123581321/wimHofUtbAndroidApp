package com.example.utb_project_wim.screens.settings

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.utb_project_wim.R
import com.example.utb_project_wim.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_second,
            container,
            false
        )
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        binding.settingsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }


        viewModel._myBundle.observe(viewLifecycleOwner, Observer<Bundle> { myBundle ->
            if (!myBundle.isEmpty) {
                val sharedPreferences = context?.getSharedPreferences("savedSettings",Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit();
                editor?.putString("name",myBundle.getString("name"))
                editor?.putInt("secsToHold",myBundle.getInt("secsToHold"))
                editor?.putInt("rounds",myBundle.getInt("rounds"))
                editor?.putInt("breaths",myBundle.getInt("breaths"))
                editor?.putString("expectedDifficulty",myBundle.getString("expectedDifficulty"))
                editor?.apply()
                findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, myBundle)
            }
        })
    }
}