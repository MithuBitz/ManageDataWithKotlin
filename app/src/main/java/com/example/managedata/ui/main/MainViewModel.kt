package com.example.managedata.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.managedata.data.MonsterRepository

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val dataRepo = MonsterRepository(app)
    val monsterData = dataRepo.monsterData

}
