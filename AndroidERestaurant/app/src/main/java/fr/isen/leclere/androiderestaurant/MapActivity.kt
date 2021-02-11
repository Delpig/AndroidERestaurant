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
import fr.isen.leclere.androiderestaurant.databinding.ActivityMapBinding
import fr.isen.leclere.androiderestaurant.models.Basket
import java.io.File
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private lateinit var binding: ActivityMapBinding
private val TAG = "MapActivity"
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * 43.12064134593929, 5.939670705189026
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val isen = LatLng(43.12064134593929, 5.939670705189026)
        mMap.addMarker(MarkerOptions()
            .position(isen)
            .title("Marker at isen"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(isen))
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