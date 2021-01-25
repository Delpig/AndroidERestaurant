package com.example.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.AlarmClock.EXTRA_MESSAGE
import com.example.androiderestaurant.databinding.ActivityFoodDetailsBinding
import com.example.androiderestaurant.databinding.ActivityHomeBinding

class FoodDetails : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.titleDetails.text = intent.getStringExtra(EXTRA_MESSAGE)
    }
}