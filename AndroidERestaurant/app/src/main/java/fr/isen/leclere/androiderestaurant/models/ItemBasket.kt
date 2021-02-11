package fr.isen.leclere.androiderestaurant.models

import com.google.gson.annotations.SerializedName

data class ItemBasket (@SerializedName("quantity") var quantity: Int, @SerializedName("items") var item: Item)