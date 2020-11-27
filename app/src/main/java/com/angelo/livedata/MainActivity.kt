package com.angelo.livedata

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.angelo.livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //We are going to use ViewBinding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //We create an instance of ViewModelProvider, which receive an object with lifecycle
        //Then the view model class reference created
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //LiveData need to pass an object with lifecycle and an Observer
        viewModel.second.observe(this, Observer { value->
            binding.tvNumber.text = value.toString()
        })

        viewModel.finished.observe(this, Observer {
            it?.let { result ->
                if(result) toast("Finished")
            }
        })

        with(binding){
            btnStart.setOnClickListener {
                val number = etNumber.text.toString()
                //Validations
                if(number.isNotEmpty() && number.length>4) {
                    viewModel.apply{
                        timerValue = number.toLong()
                        viewModel.startTimer()
                    }
                }
                else toast("Value not accepted")
            }

            btnStop.setOnClickListener {
                tvNumber.text = "0"
                etNumber.text.clear()
                viewModel.stopTimer()
            }
        }
    }

    //extension functions
    private fun Context.toast(message: String){
        Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
    }


}