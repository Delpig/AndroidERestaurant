package com.example.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.androiderestaurant.databinding.ActivityFoodBinding
import com.google.gson.Gson
import org.json.JSONObject

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


        loadData(intent.getStringExtra("category")?:"")

    }

    fun loadData(category: String){
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val postData = JSONObject()
        postData.put("id_shop", "1")


        val postRequest = JsonObjectRequest(
            Request.Method.POST, url, postData,
            {
                Log.d("Response", it.toString())
                val gson: DataResult = Gson().fromJson(it.toString(), DataResult::class.java)
                val categories: List<String> = gson.data.map{it.name}
                displayCategories(categories)
            },
            {
                Log.d("Error.Response", it.toString())
            }
        )
        queue.add(postRequest)
    }

    private fun displayCategories(categories: List<String>){
        binding.progressBar.visibility = View.GONE
        binding.progressBar.isVisible = false

        binding.FoodRecyclerView.isVisible = true

        binding.FoodRecyclerView.layoutManager =  LinearLayoutManager(this)
        binding.FoodRecyclerView.adapter = FoodAdapter(categories) {
            val intent = Intent(this, FoodDetails::class.java)
            intent.putExtra("category", it)
            startActivity(intent)
        }
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