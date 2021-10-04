package com.example.foodanywhere.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.foodanywhere.repository.NationRepository
import com.example.foodanywhere.datatype.Step
import java.util.*

class NationViewModel : ViewModel() {
    private val nationRepository =  NationRepository.get()
    val nationListLiveData = nationRepository.getNationList()
    val isExpanded: MutableMap<String, Boolean> = mutableMapOf()
}