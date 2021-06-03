package com.qm.mondia.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.qm.mondia.constants.ConstString
import com.qm.mondia.util.SharedPrefUtil.getPrefLanguage
import com.qm.mondia.util.SharedPrefUtil.setPrefLanguage
import java.util.*

/**
 * Created by Qenawi on 7/17/2020.
 **/
object LocalUtil {

    fun onAttach(base: Context): Context {
        return setLocale(base, base.getPrefLanguage())
    }

    fun setLocale(context: Context, language: String): Context {
        context.setPrefLanguage(language)
        return if (isBiggerThanOrEqualAndroidN24()) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    @SuppressLint("NewApi")
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        if (isBiggerThanOrEqualAndroidN17()) {
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
        }
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (isBiggerThanOrEqualAndroidN17())
            configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    private fun isBiggerThanOrEqualAndroidN24(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    private fun isBiggerThanOrEqualAndroidN17(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }


    fun changeLanguage(activity: AppCompatActivity) {
        val locale = Locale(activity.getPrefLanguage())
        val activityRes: Resources = activity.resources
        val activityConfig = activityRes.configuration
        if (isBiggerThanOrEqualAndroidN17())
            activityConfig.setLocale(locale)
        activityRes.updateConfiguration(activityConfig, activityRes.displayMetrics)
        val appRes: Resources = activity.applicationContext.resources
        val appConfig = appRes.configuration
        if (isBiggerThanOrEqualAndroidN17())
            appConfig.setLocale(locale)
        appRes.updateConfiguration(
            appConfig,
            appRes.displayMetrics
        )
        when (activity.getPrefLanguage()) {
            ConstString.LANG_AR -> {
                activity.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
            }
            ConstString.LANG_EN -> {
                activity.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
            }
            else -> View.LAYOUT_DIRECTION_LOCALE
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}