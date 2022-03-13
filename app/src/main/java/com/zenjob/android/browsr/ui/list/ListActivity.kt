package com.zenjob.android.browsr.ui.list

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.databinding.ActivityListBinding
import com.zenjob.android.browsr.model.data.Movie
import com.zenjob.android.browsr.ui.detail.DetailActivity
import com.zenjob.android.browsr.model.OnItemClickListener
import com.zenjob.android.browsr.ui.adapters.MovieListAdapter
import com.zenjob.android.browsr.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.snackbar.Snackbar

@AndroidEntryPoint
class ListActivity : AppCompatActivity(), OnItemClickListener {

    val mAdapter = MovieListAdapter(this)
    private val viewModel: ListViewModel by viewModels()
    private lateinit var homeBinding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_list)

        homeBinding.list.adapter = mAdapter
        viewModel.checkNetworkandFetchMovieList()

        observeMovieList()

    }

    private fun observeMovieList() {

        viewModel.showLoading.observe(this){
            if (it)
                homeBinding.progressCircular.visibility = View.VISIBLE
            else
                homeBinding.progressCircular.visibility = View.GONE
        }

        viewModel.movieList.observe(this){
            mAdapter.setMovieList(it as ArrayList<Movie>)
        }

        viewModel.showError.observe(this){

            if(!it.equals(""))
                Snackbar.make(homeBinding.root, it,Snackbar.LENGTH_LONG).setTextColor(Color.GRAY).show()

        }
    }


    override fun onMovieItemClick(
        movie: Movie
    ) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.checkNetworkandFetchMovieList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
