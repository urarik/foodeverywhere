package com.example.foodanywhere.dao.nation

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodanywhere.datatype.Nation

@Database(entities = [Nation::class], version = 1)
abstract class NationDatabase: RoomDatabase() {
    abstract fun nationDao(): NationDao
}