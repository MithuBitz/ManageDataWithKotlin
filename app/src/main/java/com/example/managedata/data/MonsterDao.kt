package com.example.managedata.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//DAO or Data Access Object is used to CRUD of the Entity Room tables or Database..

@Dao
interface MonsterDao {

    //Select data from the database
    @Query("SELECT * from monsters")
    fun getAll() : List<Monster>

    //Insert into the database
    //Single row Insert
    @Insert
    suspend fun insertMonster(monster: Monster)

    //Bulk Insert
    @Insert
    suspend fun insertMonsters(monsters: List<Monster>)

    //Delete table with annotation
    @Query("DELETE from monsters")
    suspend fun deleteAll()
}