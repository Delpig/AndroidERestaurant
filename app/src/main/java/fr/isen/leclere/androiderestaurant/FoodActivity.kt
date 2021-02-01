package fr.isen.leclere.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.leclere.androiderestaurant.databinding.ActivityFoodBinding
import fr.isen.leclere.androiderestaurant.models.DataResult
import fr.isen.leclere.androiderestaurant.models.Item
import org.json.JSONObject


class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodBinding
    private val TAG = "FoodActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        //binding.textView2.text = intent.getStringExtra(EXTRA_MESSAGE)


        loadData(intent.getStringExtra("category") ?: "")

        binding.itemsswipetorefresh.setOnRefreshListener {
            binding.FoodRecyclerView.Recycler()
            loadData(intent.getStringExtra("category") ?: "")
            binding.itemsswipetorefresh.isRefreshing = false
        }
        //methode de l'adapter à chercher


    }

    private fun loadData(category: String){
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val postData = JSONObject()
        postData.put("id_shop", "1")


        val postRequest = JsonObjectRequest(
            Request.Method.POST, url, postData,
            {
                Log.d("Response", it.toString())
                val gson: DataResult = Gson().fromJson(it.toString(), DataResult::class.java)
                gson.data.firstOrNull { it.name == category }?.items?.let {
                    displayCategories(it)
                } ?: run {
                    binding.progressBar.visibility = View.GONE
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(applicationContext, R.string.errorNoPlats, duration)
                    toast.show()
                }

            },
            {
                Log.d("Error.Response", it.toString())
                binding.progressBar.visibility = View.GONE
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, R.string.errorRetrieve, duration)
                toast.show()
            }
        )
        queue.add(postRequest)
    }

    private fun displayCategories(categories: List<Item>){
        binding.progressBar.isVisible = false
        binding.FoodRecyclerView.isVisible = true

        binding.FoodRecyclerView.layoutManager =  LinearLayoutManager(this)
        binding.FoodRecyclerView.adapter = FoodAdapter(categories) {
            val intent = Intent(this, FoodDetailsActivity::class.java)
            intent.putExtra("Items", it)
            startActivity(intent)
        }
        invalidateOptionsMenu()
    }

    override fun onStop() {
        super.onStop()
        invalidateOptionsMenu()
        Log.d(TAG, getString(R.string.logMessageFood))
    }

    override fun onDestroy() {
        super.onDestroy()
        invalidateOptionsMenu()
        Log.d(TAG, getString(R.string.logMessageFood))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_layout,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }

        R.id.action_cart -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.cartNb)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART", Context.MODE_PRIVATE)
        item.title =sharedPreference.getString("number","0")
        return super.onPrepareOptionsMenu(menu)
    }
}