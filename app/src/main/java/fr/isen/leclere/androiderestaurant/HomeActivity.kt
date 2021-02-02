package fr.isen.leclere.androiderestaurant
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.leclere.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.leclere.androiderestaurant.models.Basket
import java.io.File

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
                putExtra("category", getString(R.string.entree))
            }
            startActivity(intent)
        })

        binding.button2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FoodActivity::class.java).apply {
                putExtra("category", getString(R.string.plat))
            }
            startActivity(intent)
        })

        binding.button3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FoodActivity::class.java).apply {
                putExtra("category", getString(R.string.dessert))
            }
            startActivity(intent)
        })
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
        Log.i(TAG, getString(R.string.logMessageHome))
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