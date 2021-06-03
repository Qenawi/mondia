package com.qm.mondia.base.viewmodel

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Qenawi on 6/9/2020.
 **/
open class BaseViewModel : ViewModel(), Observable {
    private val mCallBacks: PropertyChangeRegistry = PropertyChangeRegistry()
    val mutableLiveData = MutableLiveData<Any?>()
    var isLoading = ObservableBoolean()
    override fun onCleared() {
        super.onCleared()
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallBacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallBacks.add(callback)
    }

    fun notifyChange() {
        mCallBacks.notifyChange(this, 0)
    }

    fun notifyChange(propertyId: Int) {
        mCallBacks.notifyChange(this, propertyId)
    }

    fun setValue(o: Any?) {
        mutableLiveData.value = o
    }
    fun postValue(o: Any?) {
        mutableLiveData.postValue(o)
    }

}