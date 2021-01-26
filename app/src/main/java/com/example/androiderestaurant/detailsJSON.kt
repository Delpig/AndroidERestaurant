package com.example.androiderestaurant

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Ingredients(
    @SerializedName ("id") val id: String,
    @SerializedName ("id_shop") val idShop: String,
    @SerializedName ("name_fr") val name: String,
    @SerializedName ("create_date") val createDate: String,
    @SerializedName ("update_date") val updateDate: String,
    @SerializedName ("id_pizza") val idPizza: String,
)

data class Prices(
    @SerializedName ("id") val id: String,
    @SerializedName ("id_pizza") val idPizza: String,
    @SerializedName ("id_size") val idSize: String,
    @SerializedName ("price") val price: String,
    @SerializedName ("create_date") val createDate: String,
    @SerializedName ("update_date") val updateDate: String,
    @SerializedName ("size") val size: String,

)

data class DataResult (@SerializedName("data") val data: List<Categories>):Serializable

data class Categories(
    @SerializedName ("name_fr") val name: String,
    @SerializedName ("ingredients") val ingredients: List<Ingredients>,
    @SerializedName ("images") val images: List<String>,
    @SerializedName ("prices") val prices: List<Prices>,
): Serializable {
    fun getPrice() = prices[0].price.toDouble()
    fun getFormattedPrice() = prices[0].price + "€"
    fun getFirstPicture() = if (images.isNotEmpty() && images[0].isNotEmpty()){
        images[0]
    }else {
        null
    }

    fun getAllPictures() = if (images.isNotEmpty() && images.any {it.isNotEmpty()}){
        images.filter{it.isNotEmpty()}
    }else {
        null
    }
}
