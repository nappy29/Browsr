package com.zenjob.android.browsr.model.repo

import com.zenjob.android.browsr.model.data.Movie
import com.zenjob.android.browsr.model.data.PaginatedListResponse
import com.zenjob.android.browsr.model.data.TMDBApi
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: TMDBApi): MoviesRepository {

    override suspend fun getPopularTvShows(
        query: String?,
        page: Int?
    ): Response<PaginatedListResponse<Movie>> {
        return api.getPopularTvShows(query, page)
    }

    override suspend fun getDetails(movieId: Long, query: String?): Response<Movie> {
        return api.getDetails(movieId, query)
    }
}