package com.example.utb_project_wim.screens.settings

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel




class SettingsViewModel: ViewModel() {
    private fun getEstimatedIntensity(rounds:Int? = 0, breaths:Int? = 0, secs:Int? = 0): String {
        return when(rounds!! * breaths!! * secs!!){
            0->""
            in 1..2700 -> "Low"
            in 2701..7000 -> "Medium"
            in 7001..20000 -> "High"
            else -> "Impossible"
        }
    }
     val _holt = MutableLiveData<String>()


     val _round = MutableLiveData<String>()


     val _breaths = MutableLiveData<String>()


     val _name = MutableLiveData<String>()

    val _myBundle = MutableLiveData<Bundle>()







    init {
        _holt.value="30"
        _round.value="3"
        _name.value=""
        _breaths.value="20"
        Log.i("GameViewModel", "GameViewModel created!")
    }


     fun onGo(){

         _myBundle.value=bundleOf(
             Pair("secsToHold", _holt.value.toString().toInt()),
             Pair("rounds", _round.value.toString().toInt()),
             Pair("breaths", _breaths.value.toString().toInt()),
             Pair("name", _name.value.toString()),
             Pair("expectedDifficulty",getEstimatedIntensity(_round?.value.toString()?.toInt(),_breaths?.value.toString()?.toInt(),_holt.value?.toString()?.toInt()))
             )
     }


}