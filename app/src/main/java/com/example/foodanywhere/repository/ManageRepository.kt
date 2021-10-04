package com.example.foodanywhere.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.dao.manage.ManageDao
import com.example.foodanywhere.datatype.Applicant

class ManageRepository private constructor(context: Context){
    private val manageDao = ManageDao(context)
    fun getUnConfirmedCuisineList():List<Cuisine>? = manageDao.getUnConfirmedCuisineList()
    fun getApplicantList():List<String>? = manageDao.getApplicantList()
    fun getApplicant(id: String): Applicant? = manageDao.getApplicant(id)

    fun confirmCuisine(name: String): Boolean = manageDao.confirmCuisine(name)
    fun deleteCuisine(name: String): Boolean = manageDao.deleteCuisine(name)
    fun confirmApplicant(id: String): Boolean = manageDao.confirmApplicant(id)
    fun deleteApplicant(id: String): Boolean = manageDao.deleteApplicant(id)



    companion object {
        private var INSTANCE: ManageRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null)
                INSTANCE =
                    ManageRepository(
                        context
                    )
        }
        fun get(): ManageRepository {
            return INSTANCE
                ?:
                throw IllegalAccessException("NationRepository must be initialized")
        }
    }
}