package com.qm.mondia.app

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.qm.mondia.R
import com.qm.mondia.util.gone
import com.qm.mondia.util.invisible
import com.qm.mondia.util.loadImageFromURL
import com.qm.mondia.util.visible

/**
 * Created by Qenawi on 6/18/2020.
 **/

class OtherViewsBinding {

    @BindingAdapter("setCalendarFabColor")
    fun colorFab(fab: FloatingActionButton, color: Int) {
        fab.backgroundTintList = ColorStateList.valueOf(color)
    }

    @BindingAdapter("setUnderLinedText")
    fun TextView.setUnderLined(set: Boolean) {
        if (set) {
            paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

    @BindingAdapter("imageColor")
    fun setImageColor(imageView: ImageView, color: Int?) {
        color?.let {
            val colorDrawable = ColorDrawable(color)
            imageView.setImageDrawable(colorDrawable)
        }
    }

    @BindingAdapter("android:onTextChanged")
    fun bindOnTextChange(tie: EditText, textChanged: TextWatcher) {
        tie.addTextChangedListener(textChanged)
    }

    @BindingAdapter(value = ["loadImage", "imageLoader"], requireAll = false)
    fun bindLoadImage(imageView: ImageView, obj: Any?, progressBar: ProgressBar?) {
        obj?.let {
            when (it) {
                is Int -> imageView.setImageResource(it)
                is Drawable -> imageView.setImageDrawable(it)
                is Bitmap -> imageView.setImageBitmap(it)
                is Uri -> imageView.setImageURI(it)
                is String -> {
                    imageView.loadImageFromURL(it, progressBar)
                }
                else -> {
                    imageView.loadImageFromURL("")
                }
            }
        } ?: imageView.setImageResource(R.drawable.ic_broken_image)
    }

    @BindingAdapter("app:checkForEquality", "app:observer", requireAll = true)
    fun checkForEquality(
        tilCurrent: TextInputLayout,
        tilPrev: TextInputLayout,
        hasError: MutableLiveData<Boolean>
    ) {
        hasError.value = tilCurrent.editText?.text.toString() != tilPrev.editText?.text.toString()
    }

    @BindingAdapter("app:visibleGone")
    fun bindViewGone(view: View, b: Boolean) {
        if (b) {
            view.visible()
        } else
            view.gone()
    }

    @BindingAdapter("app:visibleInVisible")
    fun bindViewInvisible(view: View, b: Boolean) {
        if (b) {
            view.visible()
        } else
            view.invisible()

    }

    @BindingAdapter("app:adapter", "app:setDivider")
    fun bindAdapter(recyclerView: RecyclerView, adapter: ListAdapter<*, *>?, divider: Boolean?) {
        adapter?.let {
            recyclerView.adapter = it
            divider?.let { div ->
                if (div) {
                    val decoration =
                        DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
                    recyclerView.addItemDecoration(decoration)
                }
            }
        }
    }

    @BindingAdapter("renderHtml")
    fun bindRenderHtml(view: TextView, description: String?) {
        if (description != null) {
            view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            view.movementMethod = LinkMovementMethod.getInstance()
        } else {
            view.text = ""
        }
    }

    @BindingAdapter("setButtonIcon")
    fun MaterialButton.setButtonIcon(@DrawableRes id: Int) {
        setIconResource(id)
    }


}