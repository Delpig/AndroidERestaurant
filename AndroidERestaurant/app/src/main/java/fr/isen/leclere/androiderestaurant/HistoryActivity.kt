package fr.isen.leclere.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.leclere.androiderestaurant.databinding.ActivityFoodDetailsBinding
import fr.isen.leclere.androiderestaurant.databinding.ActivityHistoryBinding
import fr.isen.leclere.androiderestaurant.models.Basket
import java.io.File
private lateinit var binding: ActivityHistoryBinding
private val TAG = "HistoryActivity"
class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //http://test.api.catering.bluecodegames.com/user/order
    }

    override fun onStop() {
        super.onStop()
        invalidateOptionsMenu()
        Log.d(TAG, getString(R.string.history))
    }

    override fun onDestroy() {
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i(TAG, getString(R.string.history))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_layout, menu)
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

        R.id.found -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            val intent = Intent(this, MapActivity::class.java)
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
        var nb = 0
        if(file.exists()){
            val json: Basket = gson.fromJson(file.readText(), Basket::class.java)
            for(truc in json.itemList){
                nb+=truc.quantity
            }
        }

        val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("number", nb.toString())
        editor.apply()
        item.title =sharedPreference.getString("number","0")
        return super.onPrepareOptionsMenu(menu)
    }
}