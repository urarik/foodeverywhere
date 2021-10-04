package com.example.foodanywhere.datatype

import android.graphics.Bitmap

data class Applicant(var userId: String, var image: Bitmap? =  null, var description: String) {
}