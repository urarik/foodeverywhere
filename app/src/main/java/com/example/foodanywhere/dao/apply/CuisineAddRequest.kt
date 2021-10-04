package com.example.foodanywhere.dao.apply

import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest


private const val URL = "http://ouk49957.dothome.co.kr/addCuisine.php"
private const val URL2 = "http://ouk49957.dothome.co.kr/ingredient.php"

class CuisineAddRequest(
        nationName: String,
        cuisineName: String,
        description: String,
        characteristic: Int,
        image: String,
        future: RequestFuture<String>
) : StringRequest(Method.POST, URL, future, future) {
    private val map: Map<String, String> = mapOf(
            "nation" to nationName,
            "name" to cuisineName,
            "description" to description,
            "characteristic" to characteristic.toString(),
            "img" to image
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}

class IngredientAddRequest(name: String, ingre: String, size: String) : StringRequest(Method.POST, URL2, null, null) {
    private val map: Map<String, String> = mapOf(
            "name" to name,
            "ingre" to ingre,
            "size" to size
    )

    override fun getParams(): Map<String, String> {
        return map
    }
}
