package com.example.managedata.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.managedata.LOG_TAG
import com.example.managedata.R
import com.example.managedata.utilities.FileHelper

class MainViewModel(app : Application) : AndroidViewModel(app) {
    init {
        val text = FileHelper.getTextFromResources(app, R.raw.monster_data)
        Log.i(LOG_TAG, text)
    }
}