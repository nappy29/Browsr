package com.zenjob.android.browsr

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.zenjob.android.browsr.model.data.Movie
import com.zenjob.android.browsr.model.data.PaginatedListResponse
import com.zenjob.android.browsr.model.data.TMDBApi
import com.zenjob.android.browsr.model.repo.MovieRepositoryImpl
import com.zenjob.android.browsr.util.ConnectivityUtil
import com.zenjob.android.browsr.viewmodel.ListViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var listViewModel: ListViewModel
    lateinit var moviesRepository: MovieRepositoryImpl
    private lateinit var response: PaginatedListResponse<Movie>
    private lateinit var response1: PaginatedListResponse<Movie>
    lateinit var movies: List<Movie>

    @Mock
    lateinit var apiService: TMDBApi

    private var context = mock(Context::class.java)

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        moviesRepository = MovieRepositoryImpl(apiService)
        listViewModel = ListViewModel(moviesRepository, context)

        movies = listOf(
            Movie(634649, "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.", "Spider-Man: No Way Home","2021-12-15", 8.3f,"/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"),
            Movie(414906, "In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.", "The Batman", "2022-03-01", 8f,"/74xTEgt7R36Fpooo50r9T25onhq.jpg"))

        response = PaginatedListResponse(1, 653351, 32668, movies)
        response1 = PaginatedListResponse(1, 653351, 32668, results = listOf())
    }

    @Test
    fun `First 2 movie items are correct test`() {
        runBlocking {
            Mockito.`when`(moviesRepository.getPopularTvShows())
                .thenReturn(Response.success(response))
            listViewModel.fetchMovieList()
            val result = listViewModel.movieList.getOrAwaitValue()
            assertEquals(response.results, result)
        }
    }

    @Test
    fun `empty movie list test`() {
        runBlocking {
            Mockito.`when`(moviesRepository.getPopularTvShows())
                .thenReturn(Response.success(response1))
            listViewModel.fetchMovieList()
            val result = listViewModel.movieList.getOrAwaitValue()
            assertEquals(listOf<Movie>(), result)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        try {
            afterObserve.invoke()
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("LiveData value was never set.")
            }
        } finally {
            this.removeObserver(observer)
        }
        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}