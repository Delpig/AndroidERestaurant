package com.example.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import com.example.androiderestaurant.databinding.ActivityHomeBinding

class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val TAG = "FoodActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        val param = intent.getStringExtra(EXTRA_MESSAGE)

    }

    override fun onDestroyView() {
        super.onDestroy()
        Log.i(TAG, getString(R.string.logMessageFood))
    }
}