package com.tayfuncesur.moviepedia.ui.movieDetail

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.tayfuncesur.moviepedia.BuildConfig
import com.tayfuncesur.moviepedia.R
import com.tayfuncesur.moviepedia.components.FontedMediumTextView
import com.tayfuncesur.moviepedia.databinding.MovieDetailScreenBinding
import com.tayfuncesur.moviepedia.model.Movie
import com.tayfuncesur.moviepedia.model.Status
import com.tayfuncesur.moviepedia.util.MOVIE_ID
import com.tayfuncesur.moviepedia.util.PREFS_NAME
import com.tayfuncesur.moviepedia.util.PreferencesUtil
import com.tayfuncesur.moviepedia.util.autoCleared
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.movie_detail_screen.*
import java.util.*
import javax.inject.Inject

/*
* There may be some memory leak about data binding library. Issue is here (https://github.com/square/leakcanary/issues/1137)
* */

class MovieDetailFragment : Fragment() {

    companion object {
        fun newInstance(movie: Movie) = MovieDetailFragment().apply {
            val args = Bundle()
            args.putParcelable(MOVIE_ID, movie)
            this.arguments = args
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var preferencesUtil: PreferencesUtil

    var binding by autoCleared<MovieDetailScreenBinding>()

    private lateinit var moviedDetailViewModel: MovieDetailViewModel

    private lateinit var currentMovieSummary: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_detail_screen, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        preferencesUtil = context?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )?.let { PreferencesUtil.newInstance().sharedPreferences(it) }!!
        setHasOptionsMenu(true)
        currentMovieSummary = arguments?.getParcelable(MOVIE_ID)!!
        moviedDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
        val liveData = moviedDetailViewModel.getMovieDetail(
            currentMovieSummary.id, BuildConfig.API_KEY,
            Locale.getDefault().toString().replace("_", "-")
        )
        binding.data = liveData
        binding.setLifecycleOwner(viewLifecycleOwner)


        liveData.observe(this, android.arch.lifecycle.Observer {
            if (it?.status == Status.SUCCESS) {
                var genresStr = ""
                for ((index, item) in it.data?.genres!!.withIndex()) {
                    genresStr += item.name
                    if (index != it.data.genres.size - 1) {
                        genresStr += "-"
                    }
                }
                genres.text = genresStr

                Glide.with(this).apply { RequestOptions().centerCrop() }
                    .load("https://image.tmdb.org/t/p/w342/${it.data.posterPath}")
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            avl_avatar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            avl_avatar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(image)

                if (it.data.productionCompanies.isNotEmpty()) {
                    productionLayout.visibility = View.VISIBLE
                    Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w45/${it.data.productionCompanies[0].logoPath}")
                        .into(productionImage)
                    productionText.text = it.data.productionCompanies[0].name
                }
                if (it.data.productionCountries.isNotEmpty()) {
                    productionCountryLayout.visibility = View.VISIBLE
                    productionCountryText.text = it.data.productionCountries[0].name
                }
                if (it.data.runtime > 0) {
                    timeLayout.visibility = View.VISIBLE
                    timeText.text = it.data.runtime.toString() + " " + getString(R.string.min)
                } else {
                    timeLayout.visibility = View.GONE
                }


            }
        })

        activity?.findViewById<FontedMediumTextView>(R.id.toolbar_title)?.text = currentMovieSummary.title

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.findItem(R.id.action_search)?.isVisible = false
        inflater?.inflate(R.menu.fav_menu, menu)
        if (preferencesUtil.isFav(currentMovieSummary.id)) {
            menu?.findItem(R.id.action_fav)!!.setIcon(R.drawable.ic_favorite)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_fav) {
            if (preferencesUtil.isFav(currentMovieSummary.id)) {
                item.setIcon(R.drawable.ic_favorite_outlined)
                preferencesUtil.removeFav(currentMovieSummary.id)
            } else {
                item.setIcon(R.drawable.ic_favorite)
                preferencesUtil.setFav(currentMovieSummary.id)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}