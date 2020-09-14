package com.example.managedata.data

import retrofit2.Response
import retrofit2.http.GET

interface MonsterServices {
    @GET("/feed/monster_data.json")
    suspend fun getMonsterData(): Response<List<Monster>>
}