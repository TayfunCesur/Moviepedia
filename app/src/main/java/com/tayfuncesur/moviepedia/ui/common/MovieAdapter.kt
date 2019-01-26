package com.tayfuncesur.moviepedia.ui.common

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.tayfuncesur.moviepedia.R
import com.tayfuncesur.moviepedia.model.DataTypes
import com.tayfuncesur.moviepedia.model.EventData
import com.tayfuncesur.moviepedia.model.Movie
import com.tayfuncesur.moviepedia.model.OnLoadMoreListener
import com.tayfuncesur.moviepedia.util.toUserFriendly
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.single_row.view.*
import org.greenrobot.eventbus.EventBus

class MovieAdapter(
    private var mData: List<Movie>, recyclerView: RecyclerView,
    var onLoadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<MovieAdapter.SingleRow>() {

    private val visibleThreshold = 8
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var loading: Boolean = false

    init {
        val linearLayoutManager = recyclerView
            .layoutManager as LinearLayoutManager?
        recyclerView
            .addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int, dy: Int
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalItemCount = linearLayoutManager!!.itemCount
                    if (mData.size >= 20) {
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                        if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                            onLoadMoreListener.onLoadMore()
                            loading = true
                        }
                    }

                }
            })
    }

    fun updateData(list: List<Movie>) {
        mData = list
        loading = false
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SingleRow {
        return SingleRow(LayoutInflater.from(p0.context).inflate(R.layout.single_row, p0, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(mData[p1])
    }


    inner class SingleRow(var view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {
            Glide.with(view.context).apply { RequestOptions().centerCrop() }
                .load("https://image.tmdb.org/t/p/w342/${movie.posterPath}")
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        view.avl_avatar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        view.avl_avatar.visibility = View.GONE
                        return false
                    }
                })
                .into(view.image)

            view.title.text = movie.title
            view.releaseDate.text =
                    "${view.context.getString(R.string.release_date)} ${movie.releaseDate.toUserFriendly()}"
            view.overView.text = movie.overview
            view.rateText.text = movie.voteAvarage.toString()
            view.voteText.text = movie.voteCount.toString()

            PushDownAnim.setPushDownAnimTo(view.movie_main_row).setScale(PushDownAnim.MODE_STATIC_DP, 5F)
                .setOnClickListener {
                    EventBus.getDefault().post(EventData(DataTypes.OnMovieClicked, movie))
                }

        }

    }

}