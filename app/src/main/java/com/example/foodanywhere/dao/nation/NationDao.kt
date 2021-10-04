package com.example.foodanywhere.dao.nation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.foodanywhere.datatype.Nation

@Dao
interface NationDao {

    @Query("SELECT * FROM nation")
    fun getNationList() : LiveData<List<Nation>>

}