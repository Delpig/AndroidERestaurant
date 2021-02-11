package fr.isen.leclere.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataResultAccount(@SerializedName("data") val data: AccountInfo): Serializable

