package com.tayfuncesur.moviepedia.binding

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.makeramen.roundedimageview.RoundedImageView

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(imageView: RoundedImageView, url: String) {
        Glide.with(imageView.context)
            .apply { RequestOptions().centerCrop() }
            .load("https://image.tmdb.org/t/p/w342/$url")
            .into(imageView)
    }


}