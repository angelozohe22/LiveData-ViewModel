package com.angelo.livedata

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

    private lateinit var timer: CountDownTimer

    //For a good practice
    //we must to create 2 variables if we need to call it from the activity
    private val _second = MutableLiveData<Int>()
    val second:LiveData<Int> get() = _second

    //If the function finished, we need to communicate it
    private val _finished = MutableLiveData<Boolean>()
    val finished:LiveData<Boolean> get() = _finished

    var timerValue: Long = 0

    fun startTimer(){
        timer = object : CountDownTimer(timerValue,1000){
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished/1000
                _second.value = timeLeft.toInt()
            }

            override fun onFinish() {
                _finished.value = true
            }
        }.start()
    }

    fun stopTimer(){
        timer.cancel()
    }

}