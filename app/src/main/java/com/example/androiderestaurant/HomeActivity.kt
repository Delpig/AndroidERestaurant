package com.example.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FoodActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, getString(R.string.entree))
            }
            startActivity(intent)
        })

        binding.button2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FoodActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, getString(R.string.plat))
            }
            startActivity(intent)
        })

        binding.button3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FoodActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, getString(R.string.dessert))
            }
            startActivity(intent)
        })

    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, getString(R.string.logMessageFood))
    }

    override fun onDestroy() {
       super.onDestroy()
        Log.i(TAG, getString(R.string.logMessageHome))
    }
}