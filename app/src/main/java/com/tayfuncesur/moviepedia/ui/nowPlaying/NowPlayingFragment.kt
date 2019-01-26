package com.tayfuncesur.moviepedia.ui.nowPlaying

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tayfuncesur.moviepedia.BuildConfig
import com.tayfuncesur.moviepedia.R
import com.tayfuncesur.moviepedia.databinding.NowPlayingFragmnetBinding
import com.tayfuncesur.moviepedia.model.*
import com.tayfuncesur.moviepedia.ui.common.MovieAdapter
import com.tayfuncesur.moviepedia.util.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.top_rated_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject

class NowPlayingFragment : Fragment() {

    companion object {
        fun newInstance() = NowPlayingFragment()
    }

    private var recyclerViewState: Parcelable? = null
    var loadCount = 1
    var searchLoadCount = 1
    var totalPageSize = 0
    var searchTotalPageSize = 0
    var searchQuery = ""
    private var fromSearch = false


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var binding by autoCleared<NowPlayingFragmnetBinding>()

    lateinit var nowPlayingViewModel: NowPlayingViewModel

    private lateinit var adapter: MovieAdapter
    private var list: MutableList<Movie> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.now_playing_fragmnet, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        nowPlayingViewModel = ViewModelProviders.of(this, viewModelFactory).get(NowPlayingViewModel::class.java)
        val liveData =
            nowPlayingViewModel.getNowPlaying(
                BuildConfig.API_KEY,
                Locale.getDefault().toString().replace("_", "-"),
                loadCount
            )
        binding.data = liveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        if (recyclerViewState != null) {
            mainRecycler.layoutManager!!.onRestoreInstanceState(recyclerViewState)
        }

        adapter = MovieAdapter(list, mainRecycler, object : OnLoadMoreListener {
            override fun onLoadMore() {
                if (fromSearch) {
                    if (searchLoadCount < searchTotalPageSize) {
                        searchLoadCount++
                        nowPlayingViewModel.search(
                            BuildConfig.API_KEY,
                            Locale.getDefault().toString().replace("_", "-"),
                            searchQuery,
                            searchLoadCount
                        )
                    }
                } else {
                    if (loadCount < totalPageSize) {
                        loadCount++
                        nowPlayingViewModel.getNowPlaying(
                            BuildConfig.API_KEY,
                            Locale.getDefault().toString().replace("_", "-"),
                            loadCount
                        )
                    }
                }
            }
        })

        liveData.observe(this, android.arch.lifecycle.Observer {
            if (it?.status == Status.SUCCESS) {
                // This livedata's livecycleowner is this fragment. When orientation changes, it triggers again with its initial state as normally.
                // And I used this condition to bypass it.
                if (savedInstanceState == null || fromSearch || (it.data?.page!! > 1 || !fromSearch)) {
                    if (fromSearch) {
                        if (searchTotalPageSize == 0) {
                            searchTotalPageSize = it.data!!.totalPages
                        }
                    }
                    list.addAll(it.data!!.movies)
                }
                adapter.updateData(list)

                if (totalPageSize == 0) {
                    totalPageSize = it.data.totalPages
                }

                loadMoreLayout.visibility = View.GONE
                if (loadingLayout.visibility == View.VISIBLE) {
                    loadingLayout.visibility = View.GONE
                }

                if (recyclerViewState != null) {
                    mainRecycler.layoutManager!!.onRestoreInstanceState(recyclerViewState)
                    recyclerViewState = null
                }
            } else if (it?.status == Status.LOADING) {
                if (loadCount > 1 || searchLoadCount > 1) {
                    loadMoreLayout.visibility = View.VISIBLE
                } else {
                    loadingLayout.visibility = View.VISIBLE
                }
            } else {
                loadingLayout.visibility = View.GONE
            }
        })
        mainRecycler.adapter = adapter
    }

    @Subscribe
    fun onEvent(data: EventData) {
        when (data.dataTypes) {
            DataTypes.OnQueryTextSubmit -> {
                searchQuery = data.data.toString()
                recyclerViewState = null
                fromSearch = if (data.data!!.toString().isNotEmpty()) {
                    list.clear()
                    nowPlayingViewModel.search(
                        BuildConfig.API_KEY,
                        Locale.getDefault().toString().replace("_", "-"),
                        searchQuery,
                        searchLoadCount
                    )
                    true
                } else {
                    false
                }
            }
            DataTypes.OnSearchViewClosed -> {
                restoreInitialState()
            }

            DataTypes.OnSearchViewRightIconClicked -> {
                restoreInitialState()
            }
            else -> {
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this)
        } else {
            if (EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().unregister(this)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putParcelable(
            recycler_state_now_playing,
            mainRecycler.layoutManager!!.onSaveInstanceState()!!
        )
        savedInstanceState.putParcelableArrayList(now_playing_data, list as ArrayList<out Parcelable>)
        savedInstanceState.putInt(now_playing_load_count, loadCount)
        savedInstanceState.putInt(now_playing_searched_load_count, searchLoadCount)
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onViewStateRestored(@Nullable savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(recycler_state_now_playing)
            list = savedInstanceState.getParcelableArrayList(now_playing_data)
            loadCount = savedInstanceState.getInt(now_playing_load_count)
            searchLoadCount = savedInstanceState.getInt(now_playing_searched_load_count)
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this)
        }
    }

    private fun restoreInitialState() {
        loadCount = 1
        searchLoadCount = 1
        fromSearch = false
        list.clear()
        nowPlayingViewModel.getNowPlaying(
            BuildConfig.API_KEY,
            Locale.getDefault().toString().replace("_", "-"),
            loadCount
        )
    }
}