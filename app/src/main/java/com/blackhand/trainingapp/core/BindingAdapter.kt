package com.blackhand.trainingapp.core

import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("imgUrl")
fun loadImage(img: ShapeableImageView, url: String) {
    img.load(url)
}