package com.qm.mondia.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.qm.mondia.R
import com.qm.mondia.app.BaseApplication
import com.qm.mondia.base.view.BaseFragment
import com.qm.mondia.ui.activity.MainActivity
import java.io.File
import java.lang.reflect.ParameterizedType

// MARK:- Multi purpose extension class

fun Activity.showActivity(
  destActivity: Class<out AppCompatActivity>,
  intent: Intent = Intent(this, destActivity)
) {
  this.startActivity(intent)
}

fun AppCompatActivity.findFragmentById(id: Int): Fragment? {
  supportFragmentManager.let {
    return it.findFragmentById(id)
  }
}

fun Activity.restartApp() {
  showActivity(MainActivity::class.java)
  finishAffinity()
}

fun Context.getColorFromRes(@ColorRes colorRes: Int): Int {
  return ContextCompat.getColor(this, colorRes)
}

fun <T : AndroidViewModel> T.app() = this.getApplication<BaseApplication>()
fun String.uri(): Uri? {
  return try {
    Uri.fromFile(File(this))
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

fun Activity?.dialContactPhone(phoneNumber: String) = this?.run {
  startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)))
}

fun validateData(
  size: Int?,
  progress: Boolean?
): Boolean? {
  return if (progress == false)
    (size ?: 0 > 0)
  else false
}

fun validateHolder(
  size: Int?,
  progress: Boolean?
): Boolean? {
  return if (progress == false)
    (size ?: 0 == 0)
  else false
}

fun String.isValidUrl(): Boolean {
  return try {
    URLUtil.isValidUrl(this) && Patterns.WEB_URL.matcher(this).matches()
  } catch (e: Exception) {
    false
  }
}

fun ImageView.loadImageFromURL(
  url: String?,
  progressBar: ProgressBar? = null
) {
  val mUrl = "https:$url"
  if (url.isNullOrBlank()) {
    setImageResource(R.drawable.ic_broken_image)
  } else {
    load(mUrl)
  }
}

inline fun <reified T : BaseFragment<*, *>> FragmentActivity.replaceFragment(
  bundle: Bundle? = null
) {
  val fragment = T::class.java.newInstance()
  fragment.let { myFrag ->
    supportFragmentManager.commit {
      if (!AppUtil.isOldDevice())
        setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
      myFrag.arguments = bundle
      replace(R.id.fragment_container_view, myFrag, myFrag.tag)
      addToBackStack(myFrag::class.java.name)
    }
  }
}

fun String.removeSpaces(): String = this.replace(" ", "").trim()

fun CharSequence.removeSpaces(): CharSequence = this.toString().replace(" ", "").trim()

fun Fragment.showKeyboard(show: Boolean) {
  view?.let { activity?.showKeyboard(it, show) }
}

fun Activity.showKeyboard(show: Boolean) {
  showKeyboard(currentFocus ?: View(this), show)
}

fun Context.showKeyboard(
  view: View,
  show: Boolean
) {
  with(view) {
    val inputMethodManager =
      context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (show)
      inputMethodManager.toggleSoftInput(
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY
      )
    else
      inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
  }
}

fun View.initAnimation(
  @AnimRes animRes: Int,
  seconds: Int,
  onEndCallBack: () -> Unit = {}
) {
  val animation = AnimationUtils.loadAnimation(context, animRes)
  animation.duration = seconds.toLong() * 1000
  startAnimation(animation)
  animation.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
      onEndCallBack()
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }
  })
}

fun <T : Any> Any.getTClass(): Class<T> {
  return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
}

@Suppress("UNCHECKED_CAST")
fun <B : ViewDataBinding> LifecycleOwner.bindView(container: ViewGroup? = null): B {
  return if (this is Activity) {
    val inflateMethod = getTClass<B>().getMethod("inflate", LayoutInflater::class.java)
    val invokeLayout = inflateMethod.invoke(null, this.layoutInflater) as B
    this.setContentView(invokeLayout.root)
    invokeLayout
  } else {
    val fragment = this as Fragment
    val inflateMethod = getTClass<B>().getMethod(
      "inflate",
      LayoutInflater::class.java,
      ViewGroup::class.java,
      Boolean::class.java
    )

    val lifecycle = fragment.viewLifecycleOwner.lifecycle
    if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
      error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
    }
    val invoke: B = inflateMethod.invoke(null, layoutInflater, container, false) as B
    invoke
  }
}

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(
  liveData: L,
  body: (T?) -> Unit
) {
  liveData.observe(if (this is Fragment) viewLifecycleOwner else this, Observer {
    if (lifecycle.currentState == Lifecycle.State.RESUMED) {
      body(it)
    }
  })
}

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.gone() {
  visibility = View.GONE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun LifecycleOwner.navigateSafe(
  directions: NavDirections,
  navOptions: NavOptions? = null
) {
  val navController: NavController?
  val mView: View?
  if (this is Fragment) {
    navController = findNavController()
    mView = view
  } else {
    val activity = this as Activity
    navController = activity.findNavController(R.id.fragment_container_view)
    mView = currentFocus
  }
  if (canNavigate(navController, mView)) navController.navigate(directions, navOptions)
}

fun LifecycleOwner.navigateSafe(
  @IdRes navFragmentRes: Int,
  bundle: Bundle? = null
) {
  val navController: NavController?
  val mView: View?
  if (this is Fragment) {
    navController = findNavController()
    mView = view
  } else {
    val activity = this as Activity
    navController = activity.findNavController(R.id.fragment_container_view)
    mView = currentFocus
  }
  if (canNavigate(navController, mView)) navController.navigate(navFragmentRes, bundle)
}

fun canNavigate(
  controller: NavController,
  view: View?
): Boolean {
  val destinationIdInNavController = controller.currentDestination?.id
  // add tag_navigation_destination_id to your res\values\ids.xml so that it's unique:
  val destinationIdOfThisFragment =
    view?.getTag(R.id.tag_navigation_destination_id) ?: destinationIdInNavController

  // check that the navigation graph is still in 'this' fragment, if not then the app already navigated:
  return if (destinationIdInNavController == destinationIdOfThisFragment) {
    view?.setTag(R.id.tag_navigation_destination_id, destinationIdOfThisFragment)
    true
  } else {
    Log.e("nav", "May not navigate: current destination is not the current fragment.")
    false
  }
}

fun EditText.onImeClick(callback: () -> Unit) {
  setOnEditorActionListener { _, actionId, _ ->
    callback.invoke()
    true
  }
}

fun Map<String, Any>.mMapToJsonElement(): JsonElement = this.hashToJe()
private fun Map<String, Any>.hashToJe(): JsonElement {
  val type2 = object : TypeToken<HashMap<String, Any>>() {}.type
  val mToJsonParams: String = Gson().toJson(this, type2)
  val ret = JsonParser().parse(mToJsonParams)
  return ret
}

inline fun <reified T : Any> T.mAnyToJsonElement(): JsonElement {
  val type2 = object : TypeToken<T>() {}.type
  val mToJsonParams: String = Gson().toJson(this, type2)
  return JsonParser().parse(mToJsonParams)
}

fun String.mStringToJsonElement(): JsonElement {
  return JsonParser().parse(this)
}

@Throws(JsonSyntaxException::class)
inline fun <reified T : Any> JsonElement.mMapToArrayList(): ArrayList<T> =
  try {
    mapJson(this)
  } catch (e: JsonSyntaxException) {
    e.printStackTrace()
    ArrayList()
  }

@Throws(JsonSyntaxException::class)
inline fun <reified T : Any> JsonArray.mMapToArrayListFix(): ArrayList<T> =
  try {
    val ret = ArrayList<T>()
    this.forEach { js ->
      val obj: T? = try {
        js.mMapToObject<T>()
      } catch (e: Exception) {
        e.printStackTrace()
        null
      }
      obj?.let { ob ->
        ret.add(ob)
      }
    }
    ret
  } catch (e: JsonSyntaxException) {
    e.printStackTrace()
    ArrayList()
  }

inline fun <reified T : Any> JsonElement.mMapToObject(): T? =
  try {
    mapJson(this)
  } catch (e: JsonSyntaxException) {
    null
  }

inline fun <reified T : Any> JsonObject.mMapToObject(): T? =
  try {
    mapJson(this)
  } catch (e: JsonSyntaxException) {
    null
  }

@Throws(JsonSyntaxException::class)
inline fun <reified T : Any> mapJson(je: JsonElement): T {
  val type = object : TypeToken<T>() {}.type
  return Gson().fromJson(je, type)
}

@Throws(JsonSyntaxException::class)
inline fun <reified T : Any> mapJson(je: JsonObject): T {
  val type = object : TypeToken<T>() {}.type//type
  return Gson().fromJson(je, type)
}

fun ArrayList<*>?.validateArrayList() = this != null
fun String?.isValid() = !(this.isNullOrEmpty())
fun Any?.validateType() = this != null
fun isNotNull(A: Any?): Boolean {
  return A != null
}

val emptyJe: JsonElement = HashMap<String, String>().mMapToJsonElement()
