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
import org.json.JSONObject


class FoodDetails : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.titleDetails.text = intent.getStringExtra(EXTRA_MESSAGE)

        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val postData = JSONObject()
        postData.put("id_shop", "1")


        val postRequest = JsonObjectRequest(Request.Method.POST, url, postData,
            { response -> // response
                Log.d("Response", response.toString())
            },
            { error -> // error
                Log.d("Error.Response", error.toString())
            }
        )
        queue.add(postRequest)
    }
}