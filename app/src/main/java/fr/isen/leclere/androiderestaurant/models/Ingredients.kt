package fr.isen.leclere.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ingredients(
    @SerializedName("id") val id: String,
    @SerializedName ("id_shop") val idShop: String,
    @SerializedName ("name_fr") val name: String,
    @SerializedName ("create_date") val createDate: String,
    @SerializedName ("update_date") val updateDate: String,
    @SerializedName ("id_pizza") val idPizza: String,
): Serializable

