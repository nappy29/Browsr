package com.zenjob.android.browsr.ui.detail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.databinding.ActivityDetailBinding
import com.zenjob.android.browsr.model.data.Movie
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val detailsScreenBinding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val movie = (if (intent.hasExtra("movie")) intent.getSerializableExtra("movie") as Movie else null)
            ?: return

        detailsScreenBinding.movieModel = movie

        setSupportActionBar(detailsScreenBinding.toolbar)
        supportActionBar?.title = movie.title

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        detailsScreenBinding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

}
