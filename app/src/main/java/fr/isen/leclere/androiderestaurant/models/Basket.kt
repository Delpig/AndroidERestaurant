package fr.isen.leclere.androiderestaurant.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Basket ( @SerializedName("itemList") var itemList: List<ItemBasket>
): Serializable
