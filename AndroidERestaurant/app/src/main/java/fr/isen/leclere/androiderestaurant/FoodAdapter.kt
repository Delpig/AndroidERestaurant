package fr.isen.leclere.androiderestaurant


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.leclere.androiderestaurant.databinding.RecyclerViewBinding
import fr.isen.leclere.androiderestaurant.models.Item
import com.squareup.picasso.Picasso

class FoodAdapter(val items: List<Item>, private val categoriesClickListener: (Item) -> Unit) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].name
        holder.price.text = items[position].getFormattedPrice()
        Picasso.get()
            .load(items[position].getFirstPicture())
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .resize(130, 130)
            .into(holder.logo)
        holder.layout.setOnClickListener {
            categoriesClickListener.invoke(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: RecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.listTitle
        val price = binding.listPrice
        val logo = binding.logo
        val layout = binding.root
    }
}


