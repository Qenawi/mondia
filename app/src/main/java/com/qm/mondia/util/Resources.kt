package com.qm.mondia.util

/**
 * Created by Qenawi on 6/29/2020.
 **/
data class Resources<out T>(val status: Status, val data: T? = null, val message: String? = null) {

    companion object {
        fun <T> success(data: T?, msg: String? = null): Resources<T> {
            return Resources(Status.SUCCESS, data, msg)
        }
        fun <T> message(msg: String?, data: T? = null): Resources<T> {
            return Resources(Status.MESSAGE, data, msg)
        }
        fun <T> loading(data: T?): Resources<T> {
            return Resources(Status.LOADING, data)
        }
    }


}

enum class Status {
    SUCCESS,
    MESSAGE,
    LOADING
}