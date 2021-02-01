package fr.isen.leclere.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item(
    @SerializedName("id") val id: String,
    @SerializedName("name_fr") val name: String,
    @SerializedName("id_category") val idCategory: String,
    @SerializedName("categ_name_fr") val nameCategory: String,
    @SerializedName("images") val img: List<String>,
    @SerializedName("ingredients") val ingredients: List<Ingredients>,
    @SerializedName("prices") val prices: List<Prices>
): Serializable {
    fun getPrice() = prices[0].price.toDouble()
    fun getFormattedPrice() = prices[0].price + "€"
    fun getFirstPicture() = if (img.isNotEmpty() && img[0].isNotEmpty()){
        img[0]
    }else {
        null
    }

    fun getAllPictures() = if (img.isNotEmpty() && img.any {it.isNotEmpty()}){
        img.filter{it.isNotEmpty()}
    }else {
        null
    }
}