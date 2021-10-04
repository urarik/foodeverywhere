package com.example.foodanywhere

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.io.ByteArrayInputStream


object ImageUtil {
    fun byteArrayToBinaryString(b: ByteArray): String {
        val sb = StringBuilder()
        for(i in b.indices)
            sb.append(byteToBinaryString(b[i]))
        // b.forEach { sb.append(byteToBinaryString(it)) }
        return sb.toString()
    }
    private fun byteToBinaryString(n: Byte): String {
        val sb = StringBuilder("00000000")
        for(bit in 0 until 8) {
            if(((n.toInt() shr bit) and 1) > 0) {
                sb.setCharAt(7 - bit, '1')
            }
        }
        return sb.toString()
    }
    private fun binaryStringToByteArray(s: String): ByteArray? {
        val count = s.length / 8
        val b = ByteArray(count)
        for (i in 1 until count) {
            val t = s.substring((i - 1) * 8, i * 8)
            b[i - 1] = binaryStringToByte(t)
        }
        return b
    }
    private fun binaryStringToByte(s: String): Byte {
        var ret: Byte = 0
        var total: Byte = 0
        for (i in 0..7) {
            ret = if (s[7 - i] == '1') (1 shl i).toByte() else 0
            total = (ret.toInt() or total.toInt()).toByte()
        }
        return total
    }
    fun StringToBitmap(ImageString: String?): Bitmap? {
        val modifiedString = ImageString?.substring(7) ?: throw IllegalAccessException("No Image!")
        return try {
            val bytes = binaryStringToByteArray(modifiedString)
            val bais = ByteArrayInputStream(bytes)
            BitmapFactory.decodeStream(bais)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    fun getFlag(name: String): Int {
        return when (name) {
            "Argentina" -> R.drawable.flag_argentina
            "Belgium" -> R.drawable.flag_belgium
            "Canada" -> R.drawable.flag_canada
            "China" -> R.drawable.flag_china
            "Colombia" -> R.drawable.flag_colombia
            "Dominican Republic" -> R.drawable.flag_dominicanrepublic
            "France" -> R.drawable.flag_france
            "India" -> R.drawable.flag_india
            "Indonesia" -> R.drawable.flag_indonesia
            "Italy" -> R.drawable.flag_italy
            "Jamaica" -> R.drawable.flag_jamaica
            "Japan" -> R.drawable.flag_japan
            "Korea" -> R.drawable.flag_korea
            "Mexico" -> R.drawable.flag_mexico
            "Spain" -> R.drawable.flag_spain
            "Switzerland" -> R.drawable.flag_switzerland
            "Thailand" -> R.drawable.flag_thailand
            "Turkey" -> R.drawable.flag_turkey
            "United Kingdom" -> R.drawable.flag_united_kingdom
            "United States" -> R.drawable.flag_united_states
            "Viet Nam" -> R.drawable.flag_viet_nam
            else -> throw IllegalAccessException("No flag!")
        }
    }
    fun getImg(name: String): Int {
        return when (name) {
            "Argentina" -> R.drawable.argentina
            "Belgium" -> R.drawable.belgium
            "Canada" -> R.drawable.canada
            "China" -> R.drawable.china
            "Colombia" -> R.drawable.colombia
            "Dominican Republic" -> R.drawable.dominican_republic
            "France" -> R.drawable.france
            "India" -> R.drawable.india
            "Indonesia" -> R.drawable.indonesia
            "Italy" -> R.drawable.italy
            "Jamaica" -> R.drawable.jamaica
            "Japan" -> R.drawable.japan
            "Korea" -> R.drawable.korea
            "Mexico" -> R.drawable.mexico
            "Spain" -> R.drawable.spain
            "Switzerland" -> R.drawable.switzerland
            "Thailand" -> R.drawable.thailand
            "Turkey" -> R.drawable.turkey
            "United Kingdom" -> R.drawable.united_kingdom
            "United States" -> R.drawable.united_states
            "Viet Nam" -> R.drawable.viet_nam
            else -> throw IllegalAccessException("No img!")
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageDrawable(view: ImageView, bitmap: Bitmap?) {
        view.setImageBitmap(bitmap)
    }
}