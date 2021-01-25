package com.example.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androiderestaurant.databinding.ActivityFoodBinding
import com.example.androiderestaurant.databinding.ActivityHomeBinding

class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodBinding
    private val TAG = "FoodActivity"
    private val foodEntree = arrayOf(
        Food("Salade", "Salade"),
        Food("Tomate", "Tomate"),
        Food("Oignon", "Oignon")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        binding.textView2.text = intent.getStringExtra(EXTRA_MESSAGE)

        binding.FoodRecyclerView.layoutManager =  LinearLayoutManager(this)
        binding.FoodRecyclerView.adapter = FoodAdapter(foodEntree)


    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, getString(R.string.logMessageFood))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, getString(R.string.logMessageFood))
    }


}