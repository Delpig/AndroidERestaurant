package fr.isen.leclere.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import fr.isen.leclere.androiderestaurant.databinding.RecyclerViewCartBinding
import fr.isen.leclere.androiderestaurant.models.Basket
import fr.isen.leclere.androiderestaurant.models.ItemBasket
import java.io.File

class CartAdapter(
    val items: ArrayList<ItemBasket>,
    val file: File,
    val cartActivity: CartActivity
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].item.name
        val price = items[position].quantity * items[position].item.getPrice()
        holder.price.text = price.toString()+" €"
        Picasso.get()
            .load(items[position].item.getFirstPicture())
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .resize(130, 130)
            .into(holder.logo)
        holder.nb.text = items[position].quantity.toString()
        holder.suppr.setOnClickListener {
            deleteItem(position)

        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: RecyclerViewCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.listTitleCart
        val price = binding.listPriceCart
        val logo = binding.logoCart
        val nb = binding.listQuantityCart
        val suppr = binding.suppr
    }

    fun deleteItem(position: Int) {
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        val json: Basket = gson.fromJson(file.readText(), Basket::class.java)
        if(items[position].quantity != 1){
            items[position].quantity--


            if(file.exists()){

                for(item in json.itemList){
                    if (item.item == items[position].item) {
                        item.quantity --
                        file.writeText(gson.toJson(json))
                    }
                }
                val jsonObject = gson.toJson(json)
                file.writeText(jsonObject)
                ((cartActivity).invalidateOptionsMenu())
            }

        }else{

            json.itemList.remove(items[position])
            items.removeAt(position)
            val jsonObject = gson.toJson(json)
            file.writeText(jsonObject)
            ((cartActivity).invalidateOptionsMenu())
        }
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

}