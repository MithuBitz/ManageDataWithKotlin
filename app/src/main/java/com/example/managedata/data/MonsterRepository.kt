package com.example.managedata.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.managedata.LOG_TAG
import com.example.managedata.WEB_SERVICE_URL
import com.example.managedata.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MonsterRepository(val app: Application) {

    val monsterData = MutableLiveData<List<Monster>>()

    init {
        val data = readDataFromCache()
        if (data.isEmpty()){
            refreshDataFromWeb()
        } else {
            monsterData.value = data
            Log.i(LOG_TAG, "Using Local data")
        }

    }

    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling Webservice")
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(MonsterServices::class.java)
            val serviceData = service.getMonsterData().body() ?: emptyList()
            monsterData.postValue(serviceData)
            saveDataToCache(serviceData)
        }
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    fun refreshDataFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

    //Save the server data to local store
    private fun saveDataToCache(monsterData: List<Monster>) {
        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, Monster::class.java)
        val adapter: JsonAdapter<List<Monster>> = moshi.adapter(listType)
        val json = adapter.toJson(monsterData)
        FileHelper.saveTextToFile(app, json)
    }

    //Read data from the internel storage if available
    private fun readDataFromCache(): List<Monster> {
        val json = FileHelper.readTextFile(app)
        if (json == null) {
            return emptyList()
        }
        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, Monster::class.java)
        val adapter: JsonAdapter<List<Monster>> = moshi.adapter(listType)
        return adapter.fromJson(json) ?: emptyList()
    }
}