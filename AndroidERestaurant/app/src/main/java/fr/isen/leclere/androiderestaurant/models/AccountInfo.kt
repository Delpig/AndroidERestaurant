package fr.isen.leclere.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccountInfo(
    @SerializedName("id") val id: String,
    @SerializedName("code") val code: String,
    @SerializedName("id_shop") val id_shop: String,
    @SerializedName("email") val email: String,
    @SerializedName("firstname") val firstname: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: String,
    @SerializedName("postal_code") val postal_code: String,
    @SerializedName("birth_date") val birth_date: String,
    @SerializedName("town") val town: String,
    @SerializedName("points") val points: String,
    @SerializedName("token") val token: String,
    @SerializedName("gcmtoken") val gcmtoken: String,
    @SerializedName("create_date") val create_date: String,
    @SerializedName("update_date") val update_date: String,
): Serializable
