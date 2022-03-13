package com.zenjob.android.browsr.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenjob.android.browsr.model.data.Movie
import com.zenjob.android.browsr.model.repo.MoviesRepository
import com.zenjob.android.browsr.util.ConnectivityUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val movieRepository: MoviesRepository, @ApplicationContext context: Context): ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()
    val showLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<String>()
    val context = context


    fun fetchMovieList(){
        viewModelScope.launch {
            val response = movieRepository.getPopularTvShows()

            if(response.isSuccessful){
                response.body().let{
                    showLoading.value = false
                    movieList.value = it?.results
                }
            }
            else
                showError.value = "unable to load items, please check internet connectivity"
        }
    }

    fun checkNetworkandFetchMovieList() {
        showLoading.value = true

        if(!ConnectivityUtil.isOnline(context)){
            Log.e("error oh", "No connection")
            showError.value = "No network connectivity"
            showLoading.value = false
            return
        }

        fetchMovieList()
    }

    fun checkConnectivity(): Boolean{
        if(!ConnectivityUtil.isOnline(context)){
            showError.value = "No network connectivity"
            showLoading.value = false
            return false
        }

        return true
    }
}