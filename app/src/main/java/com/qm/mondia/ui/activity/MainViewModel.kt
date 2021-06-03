package com.qm.mondia.ui.activity

import android.app.Application
import androidx.databinding.ObservableField
import com.qm.mondia.base.viewmodel.AndroidBaseViewModel

class MainViewModel(app: Application) : AndroidBaseViewModel(app) {

    val obsTitle = ObservableField<String>()



}