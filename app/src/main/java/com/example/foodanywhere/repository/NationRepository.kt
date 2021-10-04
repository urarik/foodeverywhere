package com.example.foodanywhere.repository

import android.content.Context
import android.util.Log
import android.util.LruCache
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.foodanywhere.dao.nation.NationDatabase
import com.example.foodanywhere.datatype.Cuisine
import com.example.foodanywhere.datatype.Nation

private const val DATABASE_NAME = "nation-database"

class NationRepository private constructor(context: Context, private val userCache: LruCache<String, LiveData<List<Nation>>>) {

    private val database: NationDatabase = Room.databaseBuilder(
        context.applicationContext,
        NationDatabase::class.java,
        DATABASE_NAME
    ).createFromAsset("nation-database.db").build()

    private val nationDao = database.nationDao()

    fun getNationList(): LiveData<List<Nation>> {
        val cache = userCache.get("Key")
        if(cache != null) {
            return cache
        }
        val nations = nationDao.getNationList()
        userCache.put("Key", nations)
        return nations
    }

    companion object {
        private var INSTANCE: NationRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null)
                INSTANCE =
                    NationRepository(
                        context, LruCache(1)
                    )
        }
        fun get(): NationRepository {
            return INSTANCE
                ?:
            throw IllegalAccessException("NationRepository must be initialized")
        }
    }


}