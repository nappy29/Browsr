package com.zenjob.android.browsr.model.repo

import com.zenjob.android.browsr.model.data.Movie
import com.zenjob.android.browsr.model.data.PaginatedListResponse
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesRepository {

    suspend fun getPopularTvShows(query: String? = null, page: Int? = null): Response<PaginatedListResponse<Movie>>

    suspend fun getDetails(movieId: Long, query: String? = null): Response<Movie>
}