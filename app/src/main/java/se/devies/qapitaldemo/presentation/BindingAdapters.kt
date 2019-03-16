package se.devies.qapitaldemo.presentation

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl", "error")
fun ImageView.loadImage(url: String, error: Drawable) {
    Glide.with(context).load(url).error(error).into(this)
}

@set:BindingAdapter("bind:visible")
var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else INVISIBLE
    }
