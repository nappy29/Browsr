package com.zenjob.android.browsr.ui.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun setImage(image: ImageView, url: String){
    var newUrl = "https://image.tmdb.org/t/p/w154$url"

    Glide.with(image.context).load(newUrl)
        .into(image)
}

@BindingAdapter("imageUrlLarge")
fun setImageLarge(image: ImageView, url: String){
    var newUrl = "https://image.tmdb.org/t/p/w342$url"

    Glide.with(image.context).load(newUrl)
        .into(image)
}

@BindingAdapter("getDateString")
fun getReadableDate(textView: TextView, date: String){

    var smf = SimpleDateFormat("yyyy-MM-dd")
    val dt = smf.parse(date)
    smf = SimpleDateFormat("EEEE MMMM dd,yyyy")
    val newFt = smf.format(dt)

    textView.text = "Released on: $newFt"
}