package fr.isen.leclere.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.leclere.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.leclere.androiderestaurant.models.Basket
import java.io.File

private lateinit var binding: ActivityCartBinding
private val TAG = "CartActivity"
class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadData(this)

        binding.itemsswipetorefreshCart.setOnRefreshListener {
            binding.CartRecyclerView.Recycler()
            loadData(this)
            binding.itemsswipetorefreshCart.isRefreshing = false
        }

        binding.commander.setOnClickListener{

            val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART",Context.MODE_PRIVATE)
            val account =sharedPreference.getString("id_user","0")
            if(account == "0"){
                val intent = Intent(this, CreateAccountActivity::class.java)
                startActivity(intent)
            } else{
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loadData(cartActivity: CartActivity) {
        val file = File(cacheDir.absolutePath + "UserCart.json")
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        if(file.exists()){
            val json: Basket = gson.fromJson(file.readText(), Basket::class.java)
            binding.emptyCart.isVisible = false
            binding.progressBar.isVisible = false
            binding.CartRecyclerView.isVisible = true
            binding.CartRecyclerView.layoutManager =  LinearLayoutManager(this)
            binding.CartRecyclerView.adapter = CartAdapter(json.itemList, file, cartActivity)
            invalidateOptionsMenu()
        }else{
            binding.progressBar.isVisible = false
        }



    }


    override fun onStop() {
        super.onStop()
        invalidateOptionsMenu()
        Log.d(TAG, getString(R.string.foodDetails))
    }

    override fun onDestroy() {
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i(TAG, getString(R.string.foodDetails))
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

