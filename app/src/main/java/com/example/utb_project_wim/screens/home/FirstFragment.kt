package com.example.utb_project_wim.screens.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.utb_project_wim.R
import com.example.utb_project_wim.databinding.FragmentFirstBinding
import okio.IOException
import retrofit2.HttpException





class FirstFragment : Fragment() {


    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val url  = "https://csrng.net/csrng/csrng.php?min=1&max=100"
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {


        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getWeather()
            } catch(e: IOException) {
                Log.e("HTTP", "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e("HTTP", "HttpException, unexpected response")

                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                binding.weatherMessage.text = convertRandomNumberToWeather(response.body())
            } else {
                Log.e("HTTP", "Response not successful")
            }
        }




        val sharedPreferences = context?.getSharedPreferences("savedSettings", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
           val prevName = sharedPreferences.getString("name","defaultPractice")
            val prevIntensity = sharedPreferences.getString("expectedDifficulty","normal")
            val prevRounds = sharedPreferences.getInt("rounds",3)
            val prevBreaths = sharedPreferences.getInt("breaths",20)
            val prevSecsToHold = sharedPreferences.getInt("secsToHold",30)
            binding.lastPracticeName.text = prevName
            binding.lastPracticeDifficulty.text = prevIntensity
            binding.button.setOnClickListener(){
                val myBundle = bundleOf(
                    Pair("secsToHold", prevSecsToHold),
                    Pair("rounds", prevRounds),
                    Pair("breaths", prevBreaths),
                )
                findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment, myBundle)
            }
        }
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}