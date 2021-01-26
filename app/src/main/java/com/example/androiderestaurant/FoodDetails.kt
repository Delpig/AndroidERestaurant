package com.example.androiderestaurant

import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.androiderestaurant.databinding.ActivityFoodDetailsBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject


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