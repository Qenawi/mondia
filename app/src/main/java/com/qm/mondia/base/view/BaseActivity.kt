package com.qm.mondia.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.qm.mondia.BR
import com.qm.mondia.util.LocalUtil
import com.qm.mondia.util.bindView

/**
 * Created by Qenawi on 7/17/2020.
 **/

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    val baseShowProgress = ObservableBoolean()
    protected abstract val mViewModel: VM
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        LocalUtil.changeLanguage(this)
        super.onCreate(savedInstanceState)
        LocalUtil.changeLanguage(this)
        binding = bindView()
        binding.setVariable(BR.viewModel, mViewModel)
    }



}
