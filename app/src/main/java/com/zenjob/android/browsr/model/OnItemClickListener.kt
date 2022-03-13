package com.zenjob.android.browsr.model

import android.view.View
import com.zenjob.android.browsr.model.data.Movie

interface OnItemClickListener {
        fun onMovieItemClick(
            movie: Movie
        )
}