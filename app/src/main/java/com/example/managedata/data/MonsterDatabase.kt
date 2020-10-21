package com.example.managedata.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Monster::class], version = 1, exportSchema = false)
abstract class MonsterDatabase : RoomDatabase() {

    abstract fun monsterDao(): MonsterDao

    companion object {
        @Volatile //Volatile is used to access this object with one or more thread
        private var INSTANCE : MonsterDatabase? = null

        fun getDatabase(context: Context) : MonsterDatabase {
            if (INSTANCE == null) {
                synchronized(this) { // synchronized helps to initiate one instance at a time
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MonsterDatabase::class.java,
                        "monsters.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}