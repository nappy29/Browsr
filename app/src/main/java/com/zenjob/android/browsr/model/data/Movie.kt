package com.zenjob.android.browsr.model.data

import java.io.Serializable

data class Movie(
    val id: Long,
    val overview: String?,
    val title: String,
    val release_date: String?,
    val vote_average: Float?,
    val poster_path: String
) : Serializable
