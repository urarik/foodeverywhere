package com.example.foodanywhere

import android.app.Application
import com.example.foodanywhere.repository.*
import com.example.foodanywhere.repository.ApplyRepository

class CuisineApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        NationRepository.initialize(this)
        UserRepository.initialize(this)
        ManageRepository.initialize(this)
        CuisineRepository.initialize(this)
        ApplyRepository.initialize(this)
        ReviewRepository.initialize(this)
        RecommendRepository.initialize(this)
    }
}