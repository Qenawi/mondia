package com.qm.mondia.app

import androidx.databinding.DataBindingComponent

/**
 * Created by Qenawi on 6/18/2020.
 **/
class AppDataBindingComponent : DataBindingComponent {

  override fun getOtherViewsBinding(): OtherViewsBinding {
    return OtherViewsBinding()
  }
}