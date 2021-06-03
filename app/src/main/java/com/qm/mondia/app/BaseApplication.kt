package com.qm.mondia.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil

// MARK:- BaseApplication @Docs

class BaseApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    DataBindingUtil.setDefaultComponent(AppDataBindingComponent())
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
  }
}