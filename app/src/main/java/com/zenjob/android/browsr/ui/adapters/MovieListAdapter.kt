package com.zenjob.android.browsr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.databinding.ViewholderMovieItemBinding
import com.zenjob.android.browsr.model.OnItemClickListener
import com.zenjob.android.browsr.model.data.Movie

class MovieListAdapter(val onItemCLick: OnItemClickListener) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: ViewholderMovieItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.viewholder_movie_item, parent, false)
        return MovieViewHolder(viewBinding, onItemCLick)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setMovieList(movies: ArrayList<Movie>) {

        val movieDiffCallback = MovieDiffCallback(this.movies, movies)
        val diffResult = DiffUtil.calculateDiff(movieDiffCallback)
        this.movies.clear()
        this.movies.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }


    class MovieViewHolder(private val viewBinding: ViewholderMovieItemBinding, private val onClick: OnItemClickListener) :
        RecyclerView.ViewHolder(viewBinding.root) {


        fun bind(movie: Movie) {

            viewBinding.apply {
                movieModel = movie
                onclickListener = onClick
            }

        }
    }


    class MovieDiffCallback(private val oldMovieList: List<Movie>, private val newMovieList: List<Movie>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldMovieList.size

        override fun getNewListSize() = newMovieList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMovieList[oldItemPosition].id == newMovieList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldMovieList[oldItemPosition]
            val new = newMovieList[newItemPosition]

            return old.id == new.id && old.overview == new.overview
        }
    }
}
