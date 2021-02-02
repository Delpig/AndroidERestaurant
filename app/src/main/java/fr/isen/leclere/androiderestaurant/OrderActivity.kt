package fr.isen.leclere.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.leclere.androiderestaurant.databinding.ActivityCreateAccountBinding
import fr.isen.leclere.androiderestaurant.databinding.ActivityOrderBinding
import fr.isen.leclere.androiderestaurant.models.Basket
import fr.isen.leclere.androiderestaurant.models.DataResult
import fr.isen.leclere.androiderestaurant.models.ItemBasket
import org.json.JSONObject
import java.io.File

private lateinit var binding: ActivityOrderBinding
private val TAG = "OrderActivity"
class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        sendData()
    }

    private fun sendData() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/order"

        val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART",Context.MODE_PRIVATE)
        val account =sharedPreference.getString("id_user","0")
        if(account != "0") {
            val postData = JSONObject()
            postData.put("id_shop", "1")
            postData.put("id_user", account)

            val file = File(cacheDir.absolutePath + "UserCart.json")
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val json: Basket = gson.fromJson(file.readText(), Basket::class.java)
            if(file.exists()) {
                postData.put("msg", json.toString())
                val postRequest = JsonObjectRequest(
                    Request.Method.POST, url, postData,
                    {
                        Log.d("Response", it.toString())
                        binding.progressBar2.visibility = View.GONE
                        val duration = Toast.LENGTH_LONG
                        val toast = Toast.makeText(applicationContext, R.string.commandValid, duration)
                        toast.show()

                        if(file.exists()){
                            file.delete()

                            invalidateOptionsMenu()
                        }

                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    },
                    {
                        Log.d("Error.Response", it.toString())
                        binding.progressBar2.visibility = View.GONE
                        val duration = Toast.LENGTH_LONG
                        val toast = Toast.makeText(applicationContext, R.string.ErrorCommande, duration)
                        toast.show()
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                    }
                )
                queue.add(postRequest)
            }
            else{
                binding.progressBar2.visibility = View.GONE
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(applicationContext, R.string.ErrorCommande, duration)
                toast.show()
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onStop() {
        super.onStop()
        invalidateOptionsMenu()
        Log.d(TAG, getString(R.string.logMessageAccount))
    }

    override fun onDestroy() {
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i(TAG, getString(R.string.logMessageAccount))
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
        R.id.inscription -> {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            true
        }

        R.id.connexion -> {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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
        val file = File(cacheDir.absolutePath + "UserCart.json")
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        val json: Basket = gson.fromJson(file.readText(), Basket::class.java)
        var nb = 0
        for(item in json.itemList){
            nb+=item.quantity
        }
        val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("number", nb.toString())
        editor.apply()
        item.title =sharedPreference.getString("number","0")
        return super.onPrepareOptionsMenu(menu)
    }
}