package com.example.foodanywhere.datatype

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Nation(@PrimaryKey val name: String, val description: String) {
}