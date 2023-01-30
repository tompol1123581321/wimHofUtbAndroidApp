package com.example.utb_project_wim.screens.practice
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PracticeViewModel:ViewModel() {
    private lateinit var handler : Handler
    private lateinit var runnable : Runnable
    private var originalSecsToGo :Int = 30
    private var originalHolt:Int=30
    private var originalRounds:Int=3
    private var secsToGo : Int = originalHolt
    private val _hint = MutableLiveData<String>()
    val hint: LiveData<String>
        get() = _hint

    private val _holt = MutableLiveData<String>()
    val holt: LiveData<String>
        get() = _holt

    private val _round = MutableLiveData<String>()
    val round: LiveData<String>
        get() = _round

    private val _eventPracticeFinish = MutableLiveData<Boolean>()
    val eventPracticeFinish: LiveData<Boolean>
        get() = _eventPracticeFinish




    init {
        _hint.value = "Hint"
        _holt.value=originalHolt.toString()
        _round.value=originalRounds.toString()
        Log.i("GameViewModel", "GameViewModel created!")
    }

    private fun onBreathIn() {
         _hint.value = "Breath in!"
    }


    private fun onBreathOut() {
        _hint.value = "Breath out!"
    }

    private fun breath(sec:Int){
        if(sec.mod(2)==0){
            onBreathIn()
        }
        else {
            onBreathOut()
        }
    }

    private fun onHold(){
        _hint.value = "Hold your breath"
    }


    fun onStart() {
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            println(secsToGo)
            if( _round.value.toString().toInt() > 0){
                if (secsToGo > 0){
                    breath(secsToGo)
                    secsToGo --
                }
                else {
                    if(_holt.value.toString().toInt()>0){
                        onHold()
                        _holt.value = (_holt.value.toString().toInt() - 1).toString()
                    }
                    else{
                    _round.value = (_round.value.toString().toInt() -1).toString()
                    secsToGo = originalSecsToGo
                    _holt.value=originalHolt.toString()
                    }
                }

            }
            else{
                handler.removeCallbacksAndMessages(null)
                onPracticeFinished()}
            handler.postDelayed(this.runnable, 1000)
        }
        handler.post(runnable)
        }

    fun onSetUpPracticeViewModel(breaths:Int,rounds:Int,secsToHold:Int){
        secsToGo = breaths*2
        _holt.value = secsToHold.toString()
        _round.value = rounds.toString()
        originalHolt = secsToHold
        originalSecsToGo = breaths*2
        originalRounds = rounds
    }


    fun onPracticeFinished() {
        handler.removeCallbacksAndMessages(null)
        _eventPracticeFinish.value = true
    }
}