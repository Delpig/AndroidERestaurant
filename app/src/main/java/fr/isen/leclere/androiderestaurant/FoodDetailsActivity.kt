package fr.isen.leclere.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.leclere.androiderestaurant.databinding.ActivityFoodDetailsBinding
import fr.isen.leclere.androiderestaurant.models.Basket
import fr.isen.leclere.androiderestaurant.models.Item
import fr.isen.leclere.androiderestaurant.models.ItemBasket
import java.io.File


class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailsBinding
    private val TAG = "FoodDetailsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var nbToAdd = 1
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val item = intent.getSerializableExtra("Items") as Item
        binding.titleDetails.text = item.name
        Log.d("ingredients", item.ingredients.toString())
        binding.Desc.text = item.ingredients.map { it.name }.joinToString(", ")
        binding.nbToAdd.text= nbToAdd.toString()

        binding.addCart.text ="AJOUTER AU PANIER " + ( item.getPrice().times(nbToAdd))

        binding.addbutton.setOnClickListener {
            nbToAdd++
            updateNbToAdd(nbToAdd)
            updateButtonAddCart(item, nbToAdd)
        }
        binding.removebutton.setOnClickListener {
            if (nbToAdd>1){
                nbToAdd--
                updateNbToAdd(nbToAdd)
                updateButtonAddCart(item, nbToAdd)
            }
        }

        binding.addCart.setOnClickListener{
                addToCart(item, nbToAdd)

        }

        item.getAllPictures()?.let{
            binding.logoDetails.adapter = ViewPagerAdapter(this, it)
        }
        invalidateOptionsMenu()
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

    fun updateNbToAdd(nbToAdd: Int) {
        binding.nbToAdd.text= nbToAdd.toString()
    }

    fun updateButtonAddCart(item: Item, nbToAdd: Int) {
        binding.addCart.text = "AJOUTER AU PANIER " + ( item.getPrice().times(nbToAdd))
    }

    fun addToCart(itemToAdd: Item, nbToAdd: Int) {
        val file = File(cacheDir.absolutePath + "UserCart.json")
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        //Verif existant -> Créé et écris sinon récup valeur et serialization Json et j'écris
        if(file.exists()){
            val json: Basket = gson.fromJson(file.readText(), Basket::class.java)
            var found = 0
            for(item in json.itemList){
                if (item.item == itemToAdd) {
                    found = 1
                    item.quantity += nbToAdd
                    file.writeText(gson.toJson(json))
                }
            }
            if(found == 0){
                val entry: ItemBasket = ItemBasket(nbToAdd, itemToAdd)
                json.itemList.add(entry)
            }
            val jsonObject = gson.toJson(json)
            file.writeText(jsonObject)
        }else {
            val firstEntry: ItemBasket = ItemBasket(nbToAdd, itemToAdd)
            val panier: Basket = Basket(listOf(firstEntry) as ArrayList<ItemBasket>)
            print(panier)
            val jsonObject = gson.toJson(panier)
            file.writeText(jsonObject)
        }
        val toast = Toast.makeText(
                applicationContext,
                "Item " + itemToAdd.name + " ajouté",
                Toast.LENGTH_SHORT
        )
        toast.show()

        val json: Basket = gson.fromJson(file.readText(), Basket::class.java)
        var nb = 0
        for(item in json.itemList){
            nb+=item.quantity
        }
        val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("number", nb.toString())
        editor.apply()
        invalidateOptionsMenu()
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
        val sharedPreference =  getSharedPreferences("PREFERENCE_NUMBER_CART",Context.MODE_PRIVATE)
        item.title =sharedPreference.getString("number","0")
        return super.onPrepareOptionsMenu(menu)
    }
}