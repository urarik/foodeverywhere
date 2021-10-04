package com.example.foodanywhere.datatype

import android.graphics.Bitmap
import java.io.Serializable

data class Cuisine(var nation: String,
                   var name: String,
                   var description: String = "",
                   var img: Bitmap? = null,
                   var characteristicList: List<Boolean>? = null,
                   var ingredientList: List<Ingredient>? = null) : Serializable {
}