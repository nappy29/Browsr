package com.zenjob.android.browsr

import com.zenjob.android.browsr.model.data.Movie
import com.zenjob.android.browsr.model.data.PaginatedListResponse
import com.zenjob.android.browsr.model.data.TMDBApi
import com.zenjob.android.browsr.model.repo.MovieRepositoryImpl
import com.zenjob.android.browsr.model.repo.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MovieRepositoryTest {

    lateinit var movieRepository: MoviesRepository
    private lateinit var response: PaginatedListResponse<Movie>
    lateinit var movies: List<Movie>

    @Mock
    lateinit var apiService: TMDBApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepositoryImpl(apiService)

        movies = listOf(
            Movie(634649, "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.", "Spider-Man: No Way Home","2021-12-15", 8.3f,"/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"),
            Movie(414906, "In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.", "The Batman", "2022-03-01", 8f,"/74xTEgt7R36Fpooo50r9T25onhq.jpg"))
        response = PaginatedListResponse(1, 653351, 32668, movies)
    }

    @Test
    fun `get list of movies test`() {
        runBlocking {
            Mockito.`when`(apiService.getPopularTvShows()).thenReturn(Response.success(response))
            val result = movieRepository.getPopularTvShows()
            assertEquals(response.results, result.body()?.results)
        }

    }
}